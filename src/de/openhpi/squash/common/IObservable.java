package de.openhpi.squash.common;

public interface IObservable {
	void registerObserver(IObserver observer);
	void notifyAllObservers(String message);
}
