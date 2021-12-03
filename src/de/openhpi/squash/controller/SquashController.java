package de.openhpi.squash.controller;

import de.openhpi.squash.common.Observer;
import de.openhpi.squash.model.SquashModel;
import de.openhpi.squash.view.SquashView;

public class SquashController implements Observer{
	static SquashController instance;
    private SquashView squashView;
	private SquashModel squashModel;
	
	public static void setup(SquashView squashView) {
		SquashModel sm = new SquashModel(squashView.canvasUnit,
										squashView.width, 
										squashView.height, 
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
					this.squashModel.getBallXpos(),
					this.squashModel.getBallYpos());
				break;
		}
	}
}