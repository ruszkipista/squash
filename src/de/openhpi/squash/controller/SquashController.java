package de.openhpi.squash.controller;

import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.common.Display;
import de.openhpi.squash.common.IObserver;
import de.openhpi.squash.model.BallModel;
import de.openhpi.squash.model.BoardModel;
import de.openhpi.squash.model.IMovableRectangle;
import de.openhpi.squash.model.IPositionableRectangle;
import de.openhpi.squash.model.Point;
import de.openhpi.squash.model.Speed;
import de.openhpi.squash.view.BallView;
import de.openhpi.squash.view.IDrawable;
import de.openhpi.squash.view.BoardView;

public class SquashController implements IObserver {
	private boolean modelChanged;
	private Display display;
	BoardView boardView;
	BoardModel boardModel;
	BallView ballView;
	BallModel ballModel;
	float frameTimeInSec = 0.0f;
	private Speed movSpeed;    // reused by checkCollison()
	private Point movNewPos; // reused by checkCollison()

	private List<IDrawable> shapes = new ArrayList<IDrawable>();
	
	public static void setup(Display display) {
		new SquashController(display);
	}

	// setup Views and Models
	private SquashController(Display display){
        this.frameTimeInSec = 1.0f / display.drawFrameRate;

		this.display = display;
		this.display.registerObserver(this);

		this.boardView = new BoardView();
		this.shapes.add(this.boardView);

		this.boardModel = new BoardModel(display.width, display.height);

		this.ballView = new BallView();
		this.shapes.add(this.ballView);
		
		this.ballModel = new BallModel(display.canvasUnit);
		this.ballModel.setDistancePerSecond(display.canvasUnit*4, display.canvasUnit*2);
		this.ballModel.setPosition(0, 0);
	}

	// process messages from Display
	@Override
	public void update(String message){
		switch (message){
			case "Display.SetUpReady":
				this.copyModelAttributesToViews();
				this.display.update(this.shapes);
				break;

			case "Display.NextFrame":
			case "Display.MouseClicked":
				this.calculateNextFrameInModels();
				this.processCollisonsInModels();
				if (this.finalizeNextFrameInModels()){
					this.copyModelAttributesToViews();
					this.display.update(this.shapes);
				}
				break;
		}
	}

	private void calculateNextFrameInModels(){
		this.ballModel.calculateNextFrame(this.frameTimeInSec);
	}

	private void processCollisonsInModels(){
		checkCollisonMovingInsideFixed(this.ballModel,this.boardModel);
	}

	private void checkCollisonMovingInsideFixed(IMovableRectangle movingObject, 
												IPositionableRectangle fixedObject) {
		movSpeed = movingObject.getDistancePerSecond();
		movNewPos = movingObject.getNewPosition();
		if (movSpeed.x>0 && (movingObject.top.isIntersectingWith(fixedObject.right)
						|| movingObject.bottom.isIntersectingWith(fixedObject.right))){
			movingObject.changeDistancePerSecond(-1, 1);
			movingObject.setNewPosition(movNewPos.x-(movingObject.right.pointA.x-fixedObject.right.pointA.x),
									movNewPos.y);
		}
		else if (movSpeed.x<0 && (movingObject.top.isIntersectingWith(fixedObject.left)
		 					|| movingObject.bottom.isIntersectingWith(fixedObject.left))) {
			movingObject.changeDistancePerSecond(-1, 1);
			movingObject.setNewPosition(fixedObject.left.pointA.x+(fixedObject.left.pointA.x-movNewPos.x),
									movNewPos.y);
		}

		if (movSpeed.y>0 && (movingObject.left.isIntersectingWith(fixedObject.bottom)
						|| movingObject.right.isIntersectingWith(fixedObject.bottom))){
			movingObject.changeDistancePerSecond(1, -1);
			movingObject.setNewPosition(movNewPos.x,
									movNewPos.y-(movingObject.bottom.pointA.y-fixedObject.bottom.pointA.y));
		}
		else if (movSpeed.y<0 && (movingObject.left.isIntersectingWith(fixedObject.top)
							|| movingObject.right.isIntersectingWith(fixedObject.top))){
			movingObject.changeDistancePerSecond(1, -1);
			movingObject.setNewPosition(movNewPos.x,
								fixedObject.top.pointA.y+(fixedObject.top.pointA.y-movNewPos.y));
		}
	}

	private boolean finalizeNextFrameInModels(){
		this.modelChanged = false;
		this.modelChanged = this.modelChanged || 
							this.ballModel.finalizeNextFrame();
		return modelChanged;
	}

	private void copyModelAttributesToViews(){
		this.ballView.set(this.ballModel.side, 
						this.ballModel.getNewPosition().x,
						this.ballModel.getNewPosition().y);
	}
}