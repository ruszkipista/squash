package de.openhpi.squash.common;

import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import de.openhpi.squash.model.MovableRectangleModel;
import de.openhpi.squash.model.Point;
import de.openhpi.squash.model.BoardModel;
import de.openhpi.squash.model.CounterModel;
import de.openhpi.squash.model.IFrameable;
import de.openhpi.squash.view.RectangleView;
import de.openhpi.squash.view.IDrawable;
import de.openhpi.squash.view.BoardView;
import de.openhpi.squash.view.CounterView;

public class SquashController implements IObserver {
	private Display display;
	private float frameTimeInSec = 0.0f;
	private Random random = new Random();

	private BoardView boardView;
	private BoardModel boardModel;
	private RectangleView ballView;
	private MovableRectangleModel ballModel;
	private RectangleView obstacleView;
	private MovableRectangleModel obstacleModel;
	private CounterModel counterModel;
	private CounterView  counterView;

	private List<IDrawable> shapeViews = new ArrayList<IDrawable>();
	private List<IFrameable> shapeModels = new ArrayList<IFrameable>();

	public static void main(String[] args) {
        // this call instantiates the Display applet
		PApplet.main(new String[]{Display.class.getName()});
		
		//To start the program in Fullscreen Mode, use this instead.
        //PApplet.main(new String[]{"--present",TheApp.class.getName()});
		
		new SquashController(Display.getInstance());
	}

	private SquashController(Display display){
		// setup Views and Models
        this.frameTimeInSec = 1.0f / display.drawFrameRate;

		this.display = display;
		this.display.registerObserver(this);

		this.boardView = new BoardView(display.backgroundColor);
		this.shapeViews.add(this.boardView);

		this.boardModel = new BoardModel(display.width, display.height);
		this.shapeModels.add(this.boardModel);

		this.counterModel = new CounterModel(0);
		this.shapeModels.add(this.counterModel);
		this.counterView  = new CounterView(10, 10, 111);
		this.shapeViews.add(this.counterView);

		this.ballView = new RectangleView(display.lightColor);
		this.shapeViews.add(this.ballView);
		
		this.ballModel = new MovableRectangleModel(display.canvasUnit,
													display.canvasUnit,
													Point.EPSILON,Point.EPSILON,
													display.canvasUnit*8, 
													display.canvasUnit*6);
		this.shapeModels.add(this.ballModel);

		this.obstacleView = new RectangleView(display.darkColor);
		this.shapeViews.add(this.obstacleView);
		this.obstacleModel = new MovableRectangleModel(display.canvasUnit*16,
														display.canvasUnit*10,
														display.canvasUnit*6,
														display.canvasUnit*7,
														0,0);
		this.shapeModels.add(this.obstacleModel);

		this.copyModelAttributesToViews();
	}

	// process messages from Display
	@Override
	public void update(String message){
		switch (message){
			case "Display.StartedNextFrame":
				this.calculateNextFrameInModels();
				this.processCollisonsInModels();
				if (this.finalizeNextFrameInModels()){
					this.copyModelAttributesToViews();
					this.reDrawViews();
				}
				break;

			case "Display.ClickedMouse":
				this.counterModel.increment(); 
				// the next frame will update the view on display
				break;
		}
	}

	private void calculateNextFrameInModels(){
		for (IFrameable shapeModel : shapeModels)
			shapeModel.calculateNextFrame(this.frameTimeInSec);
	}

	private void processCollisonsInModels(){
		this.ballModel.checkCollison(this.boardModel);
		this.ballModel.checkCollison(this.obstacleModel);
	}

	private boolean finalizeNextFrameInModels(){
		boolean changed = false;
		for (IFrameable shapeModel : shapeModels)
			// must execute finalizeNextFrame() before the OR operation
			changed = shapeModel.finalizeNextFrame() || changed;
		return changed;
	}

	private void copyModelAttributesToViews(){
		this.ballView.set(this.ballModel.width, 
						  this.ballModel.height,
						  this.ballModel.getPositionX(),
						  this.ballModel.getPositionY(),
						  this.ballView.color);
		int obstacleColor = (this.obstacleModel.justCollided) ? random.nextInt(256) : this.obstacleView.color;
		this.obstacleModel.justCollided = false;
		this.obstacleView.set(this.obstacleModel.width, 
								this.obstacleModel.height,
								this.obstacleModel.getPositionX(),
								this.obstacleModel.getPositionY(),
								obstacleColor);
		this.counterView.set(this.counterModel.getCount(), 
							 this.counterView.color);
	}

	private void reDrawViews() {
		for (IDrawable shapeView : shapeViews)
			shapeView.draw(this.display);
		this.display.redraw();
	}
}