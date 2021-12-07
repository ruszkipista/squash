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
import de.openhpi.squash.view.RectangleView;
import de.openhpi.squash.view.IDrawable;
import de.openhpi.squash.view.BoardView;

public class SquashController implements IObserver {
	private Display display;
	float frameTimeInSec = 0.0f;
	private boolean modelChanged; // reused by finalizeNextFrameInModels()

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
												1,1,
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
		checkCollisonMovableVsFixed(this.ballModel,this.boardModel);
	}

	private void checkCollisonMovableVsFixed(IMovableRectangle movable, 
											IPositionableRectangle fixed) {
		int[] sideCollisionCounts = new int[fixed.corners.length];
		int maxCount = 0;

		for (int i=0; i<fixed.sides.length;i++) {
			// start from 1, leave out "center" at [0]
			for (int j=1; j<movable.corners.length;j++)
				sideCollisionCounts[i] += fixed.sides[i].isIntersectingWith(movable.corners[j], movable.newCorners[j]) ? 1 : 0;
			if (maxCount < sideCollisionCounts[i]){
				maxCount = sideCollisionCounts[i];
			}
		}
		if (maxCount != 0){
			System.out.println(maxCount);
		}
	}

	// private void checkCollisonMovableInsideFixed(IMovableRectangle movable, 
	// 											IPositionableRectangle fixed) {
	// 	movSpeed = movable.getDistancePerSecond();
	// 	movNewPos = movable.getNewPosition();
	// 	if (movSpeed.x>0 && fixed.right.isIntersectingWith(movable.top, movable.bottom)){
	// 		movSpeed.negateX();
	// 		movNewPos.x -= movable.right.pointA.x - fixed.right.pointA.x;
	// 	}
	// 	else if (movSpeed.x<0 && fixed.left.isIntersectingWith(movable.top, movable.bottom)){
	// 		movSpeed.negateX();
	// 		movNewPos.x = fixed.left.pointA.x + fixed.left.pointA.x - movNewPos.x;
	// 	}

	// 	if (movSpeed.y>0 && fixed.bottom.isIntersectingWith(movable.left, movable.right)){
	// 		movSpeed.negateY();
	// 		movNewPos.y -= movable.bottom.pointA.y - fixed.bottom.pointA.y;
	// 	}
	// 	else if (movSpeed.y<0 && fixed.top.isIntersectingWith(movable.left, movable.right)){
	// 		movSpeed.negateY();
	// 		movNewPos.y = fixed.top.pointA.y + fixed.top.pointA.y - movNewPos.y;
	// 	}
	// }

	private boolean finalizeNextFrameInModels(){
		this.modelChanged = false;
		this.modelChanged = this.ballModel.finalizeNextFrame() || this.modelChanged;
		return modelChanged;
	}

	private void copyModelAttributesToViews(){
		this.ballView.set(this.ballModel.width, 
						  this.ballModel.height,
						  this.ballModel.getPosition().x,
						  this.ballModel.getPosition().y,
						  this.ballView.color);
	}
}