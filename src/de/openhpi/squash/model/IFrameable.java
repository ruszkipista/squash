package de.openhpi.squash.model;

public interface IFrameable {
    public void calculateNextFrame(float lapsedTimeInSec);
    public boolean finalizeNextFrame();
}
