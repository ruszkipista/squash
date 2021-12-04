package de.openhpi.squash.model;

public class Rectangle extends Movable {
    private float width;
    private float height;
    private Point topLeft     = new Point(0,0);
    private Point topRight    = new Point(0,0);
    private Point bottomLeft  = new Point(0,0);
    private Point bottomRight = new Point(0,0);
    private LineSegment top = new LineSegment(topLeft, topRight);
    private LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    private LineSegment left = new LineSegment(topLeft, bottomLeft);
    private LineSegment right = new LineSegment(topRight, bottomRight);

    public Rectangle(float width, float height){
        this.width = width;
        this.height = height;
    }

    public float getWidth() { return this.width;}
    public float getHeight() { return this.height;}

    @Override
    public void move(float lapsedTimeInSecond){
        super.move(lapsedTimeInSecond);
        this.setCorners();
    }    

    private void setCorners(){
        topLeft.copyPositionAndMove(super.position, 0, 0);
        topRight.copyPositionAndMove(super.position, +this.width, 0);
        bottomLeft.copyPositionAndMove(super.position, 0, +this.height);
        bottomRight.copyPositionAndMove(super.position, +this.width, +this.height);
    }

    public boolean isIntersecting(LineSegment lineSegment){
        return top.isIntersecting(lineSegment)
            || bottom.isIntersecting(lineSegment)
            || left.isIntersecting(lineSegment)
            || right.isIntersecting(lineSegment);
    }
}
