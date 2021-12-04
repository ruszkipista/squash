package de.openhpi.squash.controller;

import de.openhpi.squash.common.Observer;
import de.openhpi.squash.model.SquashModel;
import de.openhpi.squash.view.DrawableRectangle;
import de.openhpi.squash.view.SquashView;

public class SquashController implements Observer{
	static SquashController instance;
    private SquashView squashView;
	private SquashModel squashModel;
	private DrawableRectangle ball = new DrawableRectangle();
	
	public static void setup(SquashView squashView) {
		SquashModel sm = new SquashModel(squashView.canvasUnit,
										squashView.width, 
										squashView.height, 
										squashView.drawFrameRate);
		instance = new SquashController(squashView, sm);
		instance.update("Controller.SetUpReady");
	}

	// store View and Model references
	private SquashController(SquashView squashView, SquashModel squashModel){
		this.squashView = squashView;
		this.squashView.registerObserver(this);
		this.squashModel = squashModel;
		this.squashModel.registerObserver(this);
	}

	// process messages from View and Model
	@Override
	public void update(String message){
		switch (message){
			case "View.NextFrame":
				this.squashModel.calculateNextFrame();
				break;
			case "Controller.SetUpReady":
			case "Model.Changed":
				this.ball.set(this.squashModel.getBallXpos(),
							this.squashModel.getBallYpos(),
							this.squashModel.getBallWidth(),
							this.squashModel.getBallHeight());
				this.squashView.update(this.ball);
				break;
		}
	}
}