package de.openhpi.squash.model;

public class BallModel extends MovableRectangle implements Frameable{
    public float side;

    public BallModel(float side){
        super(side, side);
        this.side = side;
    }

    // Frameable
    @Override
    public void calculateNextFrame(float lapsedTimeInSec){
        super.prepareMove(lapsedTimeInSec);
	}

    // Frameable
	@Override
    public boolean finalizeNextFrame(){
        return super.finalizeMove();
	}

}
