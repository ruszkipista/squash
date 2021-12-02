package de.openhpi.counter.model;

import de.openhpi.counter.common.Observable;
import de.openhpi.counter.common.Observer;

import java.util.List;
import java.util.ArrayList;

public class CounterModel implements Observable {
	private int count = 0;
	private List<Observer> observers = new ArrayList<Observer>();

	public CounterModel(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}
	
	public void increment() {
		this.count++;
		notifyAllObservers("Model.CounterIncremented");
	}

	public void decrement() {
		if (this.count>0){
			this.count--;
			notifyAllObservers("Model.CounterDecremented");
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
