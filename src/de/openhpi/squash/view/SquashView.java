package de.openhpi.squash.view;

import processing.core.PApplet;
import java.util.List;

import de.openhpi.squash.common.Observable;
import de.openhpi.squash.common.Observer;
import de.openhpi.squash.controller.SquashController;

import java.util.ArrayList;

public class SquashView extends PApplet implements Observable{
	public float canvasUnit;
	public float drawFrameRate;

	private List<Observer> observers = new ArrayList<Observer>();

	public SquashView(){
		this.canvasUnit = 10.0f;
		this.drawFrameRate = 30.0f;
	}

	// PApplet
	@Override
	public void settings() {
		size(300, 200);
	}

	// PApplet
	@Override
	public void setup() {  // setup() runs once
		super.frameRate(this.drawFrameRate);
		// the instantiation must start from this thread
		// to be able to draw the starting state
		SquashController.setup(this);
	}

	// PApplet
	@Override
	public void draw() { // draw() loops forever, until stopped
		this.notifyAllObservers("View.NextFrame");
	}

	// PApplet
	@Override
	public void mouseClicked() {
		this.notifyAllObservers("View.MouseClicked");
	}

	public void update(Drawable shape) {
		int color = color(0);
		super.background(204);
		super.fill(color);
		shape.draw(this);
		super.redraw();
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
