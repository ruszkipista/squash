package de.openhpi.squash.controller;

import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.common.Display;
import de.openhpi.squash.common.IObserver;
import de.openhpi.squash.model.MovableRectangleModel;
import de.openhpi.squash.model.BoardModel;
import de.openhpi.squash.model.FixedRectangleModel;
import de.openhpi.squash.model.IMovableRectangle;
import de.openhpi.squash.model.IPositionableRectangle;
import de.openhpi.squash.model.Point;
import de.openhpi.squash.model.Speed;
import de.openhpi.squash.view.RectangleView;
import de.openhpi.squash.view.IDrawable;
import de.openhpi.squash.view.BoardView;

public class SquashController implements IObserver {
	private Display display;
	float frameTimeInSec = 0.0f;
	private boolean modelChanged; // reused by finalizeNextFrameInModels()
	private Speed movSpeed;       // reused by checkCollison()
	private Point movNewPos;      // reused by checkCollison()

	BoardView boardView;
	BoardModel boardModel;
	RectangleView ballView;
	MovableRectangleModel ballModel;
	RectangleView obstacleView;
	FixedRectangleModel obstacleModel;

	private List<IDrawable> shapes = new ArrayList<IDrawable>();
	
	public static void setup(Display display) {
		new SquashController(display);
	}

	// setup Views and Models
	private SquashController(Display display){
        this.frameTimeInSec = 1.0f / display.drawFrameRate;

		this.display = display;
		this.display.registerObserver(this);

		this.boardView = new BoardView(display.backgroundColor);
		this.shapes.add(this.boardView);

		this.boardModel = new BoardModel(display.width, display.height);

		this.ballView = new RectangleView(display.lightColor);
		this.shapes.add(this.ballView);
		
		this.ballModel = new MovableRectangleModel(display.canvasUnit,
												display.canvasUnit,
												0,0,
												display.canvasUnit*8, 
												display.canvasUnit*6);

		this.obstacleView = new RectangleView(display.darkColor);
		this.shapes.add(this.obstacleView);
		this.obstacleModel = new FixedRectangleModel(display.canvasUnit*16,
												display.canvasUnit*8,
												display.canvasUnit*8,
												display.canvasUnit*7);
		this.obstacleView.set(this.obstacleModel.width, 
					this.obstacleModel.height,
					this.obstacleModel.getPosition().x,
					this.obstacleModel.getPosition().y,
					this.obstacleView.color);
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
		checkCollisonMovableInsideFixed(this.ballModel,this.boardModel);
		checkCollisonMovableOutsideFixed(this.ballModel,this.obstacleModel);
	}

	private void checkCollisonMovableInsideFixed(IMovableRectangle movable, 
												IPositionableRectangle fixed) {
		movSpeed = movable.getDistancePerSecond();
		movNewPos = movable.getNewPosition();
		if (movSpeed.x>0 && fixed.right.isIntersectingWith(movable.top, movable.bottom)){
			movSpeed.reverseX();
			movNewPos.x -= movable.right.pointA.x - fixed.right.pointA.x;
		}
		else if (movSpeed.x<0 && fixed.left.isIntersectingWith(movable.top, movable.bottom)){
			movSpeed.reverseX();
			movNewPos.x = fixed.left.pointA.x + fixed.left.pointA.x - movNewPos.x;
		}

		if (movSpeed.y>0 && fixed.bottom.isIntersectingWith(movable.left, movable.right)){
			movSpeed.reverseY();
			movNewPos.y -= movable.bottom.pointA.y - fixed.bottom.pointA.y;
		}
		else if (movSpeed.y<0 && fixed.top.isIntersectingWith(movable.left, movable.right)){
			movSpeed.reverseY();
			movNewPos.y = fixed.top.pointA.y + fixed.top.pointA.y - movNewPos.y;
		}
	}

	private void checkCollisonMovableOutsideFixed(IMovableRectangle movable, 
													IPositionableRectangle fixed) {
		movSpeed = movable.getDistancePerSecond();
		movNewPos = movable.getNewPosition();
		if (movSpeed.x>0 && fixed.left.isIntersectingWith(movable.top, movable.bottom)){
			movSpeed.reverseX();
			movNewPos.x -= movable.right.pointA.x - fixed.left.pointA.x;
		}
		else if (movSpeed.x<0 && fixed.right.isIntersectingWith(movable.top, movable.bottom)) {
			movSpeed.reverseX();
			movNewPos.x = fixed.right.pointA.x + fixed.right.pointA.x - movNewPos.x;
		}

		if (movSpeed.y>0 && fixed.top.isIntersectingWith(movable.left, movable.right)){
			movSpeed.reverseY();
			movNewPos.y -= movable.bottom.pointA.y - fixed.top.pointA.y;
		}
		else if (movSpeed.y<0 && fixed.bottom.isIntersectingWith(movable.left, movable.right)) {
			movSpeed.reverseY();
			movNewPos.y = fixed.bottom.pointA.y + fixed.bottom.pointA.y - movNewPos.y;
		}
	}

	private boolean finalizeNextFrameInModels(){
		this.modelChanged = false;
		this.modelChanged = this.modelChanged || this.ballModel.finalizeNextFrame();
		return modelChanged;
	}

	private void copyModelAttributesToViews(){
		this.ballView.set(this.ballModel.width, 
						  this.ballModel.height,
						  this.ballModel.getNewPosition().x,
						  this.ballModel.getNewPosition().y,
						  this.ballView.color);
	}
}