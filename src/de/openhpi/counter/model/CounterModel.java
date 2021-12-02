package de.openhpi.counter.model;

import de.openhpi.counter.common.Observable;
import de.openhpi.counter.common.Observer;
import de.openhpi.counter.common.ModelObserver;

import java.util.List;
import java.util.ArrayList;

public class CounterModel implements Observable {

	private int count = 0;
	private List<Observer> observers = new ArrayList<Observer>();

	public int getCount() {
		return count;
	}
	
	public CounterModel(int count) {
		this.count = count;
	}

	public void increment() {
		this.count++;
		notifyAllObservers();
	}
	
	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);	
	}
	
	@Override
	public void notifyAllObservers() {
		for (Observer observer : this.observers) {
			((ModelObserver)observer).updateFromModel();
		}
	}
}
