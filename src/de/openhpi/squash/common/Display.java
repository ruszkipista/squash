package de.openhpi.squash.common;

import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.view.IDrawable;

import de.openhpi.squash.controller.SquashController;

public class Display extends PApplet implements IObservable{
	public int canvasWidth;
	public int canvasHeight;
	public float canvasUnit;
	public float drawFrameRate;
	public int backgroundColor;
	public int darkColor;
	public int lightColor;

	private List<IObserver> observers = new ArrayList<IObserver>();

	public static void main(String[] args) {
        // this call instantiates the Display applet
		PApplet.main(new String[]{Display.class.getName()});
		
		//To start the program in Fullscreen Mode, use this instead.
        //PApplet.main(new String[]{"--present",TheApp.class.getName()});
	}

	public Display(){
		this.canvasWidth = 300;
		this.canvasHeight = 210;
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
	public void draw() { // draw() calles 'drawFrameRate' times per second
		this.notifyAllObservers("Display.NextFrame");
	}

	// PApplet
	@Override
	public void mouseClicked() {
		this.notifyAllObservers("Display.MouseClicked");
	}

	public void update(List<IDrawable> shapes) {
		for (IDrawable shape : shapes)
			shape.draw(this);
		super.redraw();
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
