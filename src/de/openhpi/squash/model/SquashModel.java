package de.openhpi.squash.model;

import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.common.Observable;
import de.openhpi.squash.common.Observer;


public class SquashModel implements Observable {
	private List<Observer> observers = new ArrayList<Observer>();
	private Space space;

	public SquashModel(float baseUnit,float width, float height) {
		this.space = new Space(width,height);
	}

	public void calculateNextFrame(float lapsedTimeInSec){
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
