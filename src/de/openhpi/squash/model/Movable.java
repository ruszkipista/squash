package de.openhpi.squash.model;

public abstract class Movable {
    boolean drawable = false;

    Point position = new Point(0,0);
    Speed distancePerSecond = new Speed(0,0);

    public void move(float lapsedTimeInSecond){
        this.position.move(distancePerSecond, lapsedTimeInSecond);
    }

    public Point getPosition(){
        return this.position;
    }
    public void setPosition(float x, float y){
        this.position.set(x,y);
    }

    public void setDistancePerSecond(float x, float y){
        this.distancePerSecond.set(x,y);
    }

    public void setDrawableOn(){
        this.drawable = true;
    }

    public void setDrawableOff(){
        this.drawable = false;
    }

    public void changeSpeed(float x, float y){
        distancePerSecond.change(x,y);
    }

}
