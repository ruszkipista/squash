package de.openhpi.squash.model;

public class BoardModel extends IPositionableRectangle implements IFrameable{

	public BoardModel(float width, float height) {
		super(width, height, 0, 0);
	}

    // Frameable
    @Override
    public void calculateNextFrame(float lapsedTimeInSec){
	}

    // Frameable
	@Override
    public boolean finalizeNextFrame(){
		return false; // no change - no need to re-draw
	}   	

}
