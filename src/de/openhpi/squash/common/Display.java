package de.openhpi.squash.common;

import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.view.Drawable;

import de.openhpi.squash.controller.SquashController;

public class Display extends PApplet implements Observable{
	public int canvasWidth;
	public int canvasHeight;
	public float canvasUnit;
	public float drawFrameRate;
	public int backgroundColor;
	public int darkColor;
	public int lightColor;

	private List<Observer> observers = new ArrayList<Observer>();

	public static void main(String[] args) {
        // this call instantiates the Display applet
		PApplet.main(new String[]{Display.class.getName()});
		
		//To start the program in Fullscreen Mode, use this instead.
        //PApplet.main(new String[]{"--present",TheApp.class.getName()});
	}

	public Display(){
		this.canvasWidth = 300;
		this.canvasHeight = 200;
		this.canvasUnit = 10.0f;
		this.drawFrameRate = 30.0f;
		this.darkColor = 0;
		this.lightColor = 255;	
		this.backgroundColor = 204;
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
		// the instantiation must start from this thread
		// to be able to draw the starting state
		SquashController.setup(this);
		this.notifyAllObservers("Display.SetUpReady");
	}

	// PApplet
	@Override
	public void draw() { // draw() loops forever, until stopped
		this.notifyAllObservers("Display.NextFrame");
	}

	// PApplet
	@Override
	public void mouseClicked() {
		this.notifyAllObservers("Display.MouseClicked");
	}

	public void update(List<Drawable> shapes) {
		for (Drawable shape : shapes)
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
