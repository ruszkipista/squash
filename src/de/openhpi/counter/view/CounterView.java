package de.openhpi.counter.view;

import de.openhpi.counter.common.Observable;
import de.openhpi.counter.common.Observer;

import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;

public class CounterView extends PApplet implements Observable{
	// store the first instance
	private static CounterView firstInstance = null;

	private List<Observer> observers = new ArrayList<Observer>();

	public CounterView(){
		if (firstInstance == null)
		    firstInstance = this;
	}
	public static CounterView getInstance(){
		return firstInstance;
	}

	// PApplet
	@Override
	public void settings() {
		size(200, 200);
	}

	// PApplet
	@Override
	public void setup() {  // setup() runs once
		super.frameRate(30);
		this.notifyAllObservers("View.SetUpReady");
	}

	// PApplet
	@Override
	public void draw() { // draw() loops forever, until stopped
	}

	// PApplet
	@Override
	public void mouseClicked() {
		this.notifyAllObservers("View.MouseClicked");
	}

	public void update(int xPos) {
		super.background(204);
		super.fill(0);
		super.rect(xPos, 10, 10, 10);
		super.redraw();
	}
	
	// Observable
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);	
	}
	// Observable
	@Override
	public void notifyAllObservers(String message) {
		for (Observer observer : this.observers)
			observer.update(message);
	}
}
