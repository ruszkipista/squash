package de.openhpi.squash.controller;

import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.common.Display;
import de.openhpi.squash.common.IObserver;
import de.openhpi.squash.model.MovableRectangleModel;
import de.openhpi.squash.model.BoardModel;
import de.openhpi.squash.model.Collison;
import de.openhpi.squash.model.FixedRectangleModel;
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
												0,0,
												display.canvasUnit*8, 
												display.canvasUnit*6);

		this.obstacleView = new RectangleView(display.darkColor);
		this.shapes.add(this.obstacleView);
		this.obstacleModel = new FixedRectangleModel(display.canvasUnit*16,
												display.canvasUnit*10,
												display.canvasUnit*6,
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
		Collison.checkMovableVsFixed(this.ballModel,this.boardModel);
		Collison.checkMovableVsFixed(this.ballModel,this.obstacleModel);
	}

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