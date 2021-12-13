package de.openhpi.squash.common;

import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;

public class Display extends PApplet implements IObservable{
	private static Display instance;
	public int   canvasWidth;
	public int   canvasHeight;
	public float canvasUnit;
	public float drawFrameRate;
	public int   backgroundColor;
	public int   darkColor;
	public int   lightColor;

	private List<IObserver> observers = new ArrayList<IObserver>();

	public Display(){
		instance = this;
		this.canvasWidth     = 290;
		this.canvasHeight    = 202;
		this.canvasUnit      = 10.0f;
		this.drawFrameRate   = 30.0f;
		this.darkColor       = 0;
		this.lightColor      = 255;	
		this.backgroundColor = 204;
	}

	public static Display getInstance(){
		return instance;
	}

	// PApplet
	@Override
	public void settings() {
		size(this.canvasWidth, this.canvasHeight);
	}

	// PApplet
	@Override
	public void setup() {  // setup() runs once
		super.frameRate(this.drawFrameRate);
	}

	// PApplet
	@Override
	public void draw() { // draw() calles 'drawFrameRate' times per second
		this.notifyAllObservers("Display.NextFrame");
	}

	// PApplet
	@Override
	public void mouseClicked() {
		this.notifyAllObservers("Display.MouseClicked");
	}

	// Observable
	@Override
	public void registerObserver(IObserver observer) {
		this.observers.add(observer);	
	}
	// Observable
	@Override
	public void notifyAllObservers(String message) {
		for (IObserver observer : this.observers)
			observer.update(message);
	}
}
