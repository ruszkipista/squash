package de.openhpi.squash.controller;

import de.openhpi.squash.common.Observer;
import de.openhpi.squash.model.SquashModel;
import de.openhpi.squash.view.SquashView;

public class SquashController implements Observer{
	static SquashController instance;
    private SquashView squashView;
	private SquashModel squashModel;
	
	public static void setup(SquashView squashView) {
		SquashModel sm = new SquashModel(
			translatePixelToUnit(squashView.width, squashView.canvasUnit), 
			translatePixelToUnit(squashView.height, squashView.canvasUnit),
			squashView.drawFrameRate);
		instance = new SquashController(squashView, sm);
		instance.update("Controller.SetUpReady");
	}

	// store references to View and Model
	private SquashController(SquashView squashView, SquashModel squashModel){
		this.squashView = squashView;
		this.squashView.registerObserver(this);
		this.squashModel = squashModel;
		this.squashModel.registerObserver(this);
	}

	public static SquashController getInstance(){ return instance;}

	// process messages
	@Override
	public void update(String message){
		switch (message){
			case "View.MouseClicked": 
				this.squashModel.increment();
				break;
			case "View.NextFrame":
				this.squashModel.calculateNextFrame();
				break;
			case "Controller.SetUpReady":
			case "Model.Changed":
				this.squashView.update(
					translateUnitToPixel(this.squashModel.getBallXpos()),
					translateUnitToPixel(this.squashModel.getBallYpos()));
				break;
		}
	}
	private int translateUnitToPixel(double unit){
		return (int) Math.round(unit * squashView.canvasUnit);
	}
	private static double translatePixelToUnit(int pixel, int canvasUnit){
		return ((double) pixel) / canvasUnit;
	}	
}