package de.openhpi.squash.controller;

import de.openhpi.squash.common.Observer;
import de.openhpi.squash.model.BallModel;
import de.openhpi.squash.view.BallView;

public class BallController implements Observer{
    private BallView ballView;
	private BallModel ballModel;
	private float frameTimeInSec;
	
	// store View and Model references
	public BallController(BallView ballView, BallModel ballModel, float frameTimeInSec){
		this.ballView = ballView;
		this.ballView.registerObserver(this);
		this.ballModel = ballModel;
		this.ballModel.registerObserver(this);
		this.frameTimeInSec = frameTimeInSec;
	}

	// process messages from View and Model
	@Override
	public void update(String message){
		switch (message){
			case "View.NextFrame":
				this.ballModel.calculateNextFrame(this.frameTimeInSec);
				break;
			case "Model.Changed":
				this.ballView.set(this.ballModel.getPosition().x,
								this.ballModel.getPosition().y,
								this.ballModel.getWidth(),
								this.ballModel.getHeight());
				break;
		}
	}
}