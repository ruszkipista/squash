package de.openhpi.squash.model;

public interface Frameable {
    public void calculateNextFrame(float lapsedTimeInSec);
    public boolean finalizeNextFrame();
}
