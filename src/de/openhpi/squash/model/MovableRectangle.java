package de.openhpi.squash.model;

public abstract class MovableRectangle extends PositionableRectangle {
    private Speed distancePerSecond = new Speed(0,0);

    public MovableRectangle(float width, float height){
        super(width, height);
    }
    
    public void move(float lapsedTimeInSecond){
        super.position.move(distancePerSecond, lapsedTimeInSecond);
        super.setCorners();
    }

    public void setDistancePerSecond(float x, float y){
        this.distancePerSecond.set(x,y);
    }

    public void changeSpeed(float x, float y){
        distancePerSecond.change(x,y);
    }

}
