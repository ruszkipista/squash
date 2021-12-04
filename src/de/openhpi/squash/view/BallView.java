package de.openhpi.squash.view;

import java.util.List;
import java.util.ArrayList;

import de.openhpi.squash.common.Observable;
import de.openhpi.squash.common.Observer;

public class BallView extends Drawable implements Observable {
    private List<Observer> observers = new ArrayList<Observer>();
    private float xPos;
    private float yPos;
    private float width;
    private float height;

    public void set(float xPos, float yPos, float width, float height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void draw(SquashView display){
        display.fill(display.color(display.darkColor));
        display.rect(this.xPos, this.yPos, this.width, this.height);
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
