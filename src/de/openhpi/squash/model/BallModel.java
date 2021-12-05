package de.openhpi.squash.model;

public class BallModel extends Movable implements Frameable{
    public float side;

    private Point topLeft     = new Point(0,0);
    private Point topRight    = new Point(0,0);
    private Point bottomLeft  = new Point(0,0);
    private Point bottomRight = new Point(0,0);
    private LineSegment top = new LineSegment(topLeft, topRight);
    private LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    private LineSegment left = new LineSegment(topLeft, bottomLeft);
    private LineSegment right = new LineSegment(topRight, bottomRight);

    public BallModel(float side){
        this.side = side;
    }

    // Movable
    @Override
    public void move(float lapsedTimeInSecond){
        super.move(lapsedTimeInSecond);
        this.setCorners();
    }    

    private void setCorners(){
        topLeft.copyPositionAndMove(super.position, 0, 0);
        topRight.copyPositionAndMove(super.position, +this.side, 0);
        bottomLeft.copyPositionAndMove(super.position, 0, +this.side);
        bottomRight.copyPositionAndMove(super.position, +this.side, +this.side);
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
