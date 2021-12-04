package de.openhpi.squash.model;

import java.util.List;

import de.openhpi.squash.common.Observable;
import de.openhpi.squash.common.Observer;

import java.util.ArrayList;

public class SquashModel implements Observable {
	private List<Observer> observers = new ArrayList<Observer>();
	private float frameTimeInSec = 0.0f;
	private Space space;
	private Rectangle ball;

	public SquashModel(float baseUnit,float width, float height, float frameRate) {
		this.frameTimeInSec = 1.0f / frameRate;
		this.space = new Space(width,height);

		this.ball = new Rectangle(baseUnit,baseUnit);
		this.ball.setPosition(0,0);
		this.ball.setDistancePerSecond(baseUnit*4, baseUnit*2);
	}

	public float getBallXpos() { return ball.getPosition().x; }
	public float getBallYpos() { return ball.getPosition().y; }
	public float getBallWidth(){ return ball.getWidth(); }
	public float getBallHeight(){ return ball.getHeight(); }

	public void calculateNextFrame(){
		ball.move(this.frameTimeInSec);
		notifyAllObservers("Model.Changed");
	}

	// Observable
	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);	
	}
	// Observable
	@Override
	public void notifyAllObservers(String message) {
		for (Observer observer : this.observers)
			observer.update(message);
	}
}
