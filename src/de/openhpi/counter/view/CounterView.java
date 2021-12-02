package de.openhpi.counter.view;

import de.openhpi.counter.common.Observable;
import de.openhpi.counter.common.Observer;
import de.openhpi.counter.common.ViewObserver;

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
	@Override
	public void settings() {
		size(200, 200);
	}

	@Override
	public void setup() {  // setup() runs once
		super.frameRate(30);
		this.update(0);
	}

	@Override
	public void draw() {
	}  // draw() loops forever, until stopped
	
	@Override
	public void mouseClicked() {
		this.notifyAllObservers();
	}

	public void update(int xPos) {
		super.background(204);
		super.fill(0);
		super.rect(xPos, 10, 10, 10);
		super.redraw();
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);	
	}
	
	@Override
	public void notifyAllObservers() {
		for (Observer observer : this.observers) {
			((ViewObserver)observer).updateFromView();;
		}
	}
}
