package de.openhpi.squash.model;

public abstract class MovableRectangle extends Rectangle implements Frameable {
    public boolean drawable = false;

    private Point topLeft     = new Point(0,0);
    private Point topRight    = new Point(0,0);
    private Point bottomLeft  = new Point(0,0);
    private Point bottomRight = new Point(0,0);
    private LineSegment top = new LineSegment(topLeft, topRight);
    private LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    private LineSegment left = new LineSegment(topLeft, bottomLeft);
    private LineSegment right = new LineSegment(topRight, bottomRight);

    Point position = new Point(0,0);
    Speed distancePerSecond = new Speed(0,0);

    public MovableRectangle(float width, float height){
        super(width, height);
        this.setCorners();
    }

    public void move(float lapsedTimeInSecond){
        this.position.move(distancePerSecond, lapsedTimeInSecond);
        this.setCorners();
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
    
    private void setCorners(){
        topLeft.copyPositionAndMove(this.position, 0, 0);
        topRight.copyPositionAndMove(this.position, +this.width, 0);
        bottomLeft.copyPositionAndMove(this.position, 0, +this.height);
        bottomRight.copyPositionAndMove(this.position, +this.width, +this.height);
    }

    public boolean isIntersecting(LineSegment lineSegment){
        return top.isIntersecting(lineSegment)
            || bottom.isIntersecting(lineSegment)
            || left.isIntersecting(lineSegment)
            || right.isIntersecting(lineSegment);
    }

    // Frameable
    public void calculateNextFrame(float lapsedTimeInSec){
        this.move(lapsedTimeInSec);
	}
}
