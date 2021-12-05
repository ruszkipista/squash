package de.openhpi.squash.model;

public class BoardModel extends Rectangle implements Frameable{

	public BoardModel(float width, float height) {
		super(width, height);
	}

	// Frameable
	public void calculateNextFrame(float lapsedTimeInSec){
	}

}
