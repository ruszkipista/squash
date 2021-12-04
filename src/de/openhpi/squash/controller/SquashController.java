package de.openhpi.squash.controller;

import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.common.Observer;
import de.openhpi.squash.model.BallModel;
import de.openhpi.squash.model.SquashModel;
import de.openhpi.squash.view.BallView;
import de.openhpi.squash.view.Drawable;
import de.openhpi.squash.view.SquashView;

public class SquashController implements Observer{
	static SquashController instance;
    private SquashView squashView;
	private SquashModel squashModel;
	private BallController ballController;
	float frameTimeInSec = 0.0f;

	private List<Drawable> shapes = new ArrayList<Drawable>();
	
	public static void setup(SquashView squashView) {
		SquashModel sm = new SquashModel(squashView.canvasUnit,
										squashView.width, 
										squashView.height);
		instance = new SquashController(squashView, sm);
		instance.update("Controller.SetUpReady");
	}

	// store View and Model references
	private SquashController(SquashView squashView, SquashModel squashModel){
        this.frameTimeInSec = 1.0f / squashView.drawFrameRate;

		this.squashView = squashView;
		this.squashView.registerObserver(this);
		this.squashModel = squashModel;
		this.squashModel.registerObserver(this);

		BallView ballView = new BallView();
		BallModel ballModel = new BallModel(squashView.canvasUnit,
											squashView.canvasUnit);
		this.ballController = new BallController(ballView, ballModel,this.frameTimeInSec);
	}

	// process messages from View and Model
	@Override
	public void update(String message){
		switch (message){
			case "View.NextFrame":
				this.squashModel.calculateNextFrame(this.frameTimeInSec);
				break;
			case "Controller.SetUpReady":
			case "Model.Changed":
				this.squashView.update(this.shapes);
				break;
		}
	}
}