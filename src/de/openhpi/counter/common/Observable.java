package de.openhpi.counter.common;

public interface Observable {
	void registerObserver(Observer observer);
	void notifyAllObservers(String message);
}
