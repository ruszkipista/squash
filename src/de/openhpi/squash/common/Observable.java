package de.openhpi.squash.common;

public interface Observable {
	void registerObserver(Observer observer);
	void notifyAllObservers(String message);
}
