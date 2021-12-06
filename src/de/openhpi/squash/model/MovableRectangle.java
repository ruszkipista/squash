package de.openhpi.squash.model;

public class MovableRectangle extends IMovableRectangle implements IFrameable{

    public MovableRectangle(float width,  float height,
                            float posX,   float posY,
                            float speedX, float speedY){
        super(width, height, posX, posY, speedX, speedY);
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
