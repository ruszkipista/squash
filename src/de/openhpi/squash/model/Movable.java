package de.openhpi.squash.model;

public abstract class Movable {
    boolean drawable = false;

    Point positionMidPoint = new Point(0,0);
    Point distancePerSecond = new Point(0,0);

    public void move(double timeInSecond){
        this.positionMidPoint.move(distancePerSecond, timeInSecond);
    }

    public Point getMidPoint(){
        return this.positionMidPoint;
    }
    public void setPositionMidpoint(double x, double y){
        this.positionMidPoint.set(x,y);
    }

    public void setDistancePerSecond(double x, double y){
        this.distancePerSecond.set(x,y);
    }

    public void setDrawableOn(){
        this.drawable = true;
    }

    public void setDrawableOff(){
        this.drawable = false;
    }

}
