package de.openhpi.squash.model;

public class BoardModel implements Frameable{
	public float width;
	public float height;

	public BoardModel(float width, float height) {
		this.width = width;
		this.height = height;
	}

	// Frameable
	public void calculateNextFrame(float lapsedTimeInSec){
	}

}
