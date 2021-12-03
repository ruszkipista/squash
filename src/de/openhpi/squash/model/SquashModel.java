package de.openhpi.squash.model;

import java.util.List;

import de.openhpi.squash.common.Observable;
import de.openhpi.squash.common.Observer;

import java.util.ArrayList;

public class SquashModel implements Observable {
	private List<Observer> observers = new ArrayList<Observer>();
	private double frameTimeInSec = 0.0d;
	private Space space;
	private Ball ball;
	private double unit = 1.0d;

	public SquashModel(double width, double height, double frameRate) {
		this.frameTimeInSec = 1.0d / frameRate;
		this.space = new Space(width,height);
		this.ball = new Ball(unit,unit);
		this.ball.setPositionMidpoint(0+unit/2, 0+unit/2);
		this.ball.setDistancePerSecond(unit, unit/2);
	}

	public double getBallXpos() {
		return ball.getMidPoint().x - unit/2;
	}
	public double getBallYpos() {
		return ball.getMidPoint().y - unit/2;
	}

	public void calculateNextFrame(){
		ball.move(this.frameTimeInSec);
		notifyAllObservers("Model.Changed");
	}

	public void increment() {
		ball.move(1);
		notifyAllObservers("Model.Changed");
	}

	public void decrement() {
		if (this.ball.getMidPoint().x > 0){
			this.ball.move(-1);
			notifyAllObservers("Model.Changed");
		}
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
