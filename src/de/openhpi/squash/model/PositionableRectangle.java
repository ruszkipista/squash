package de.openhpi.squash.model;

public abstract class PositionableRectangle extends Rectangle {
    protected Point position = new Point(0,0);

    private Point topLeft     = new Point(0,0);
    private Point topRight    = new Point(0,0);
    private Point bottomLeft  = new Point(0,0);
    private Point bottomRight = new Point(0,0);
    private LineSegment top    = new LineSegment(topLeft,    topRight);
    private LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    private LineSegment left   = new LineSegment(topLeft,    bottomLeft);
    private LineSegment right  = new LineSegment(topRight,   bottomRight);

    public PositionableRectangle(float width, float height){
        super(width, height);
        this.setCorners();
    }
    
    protected void setCorners(){
        topLeft.copyPositionAndMove(this.position, 0, 0);
        topRight.copyPositionAndMove(this.position, +super.width, 0);
        bottomLeft.copyPositionAndMove(this.position, 0, +super.height);
        bottomRight.copyPositionAndMove(this.position, +super.width, +super.height);
    }

    public Point getPosition(){
        return this.position;
    }
    public void setPosition(float x, float y){
        this.position.set(x,y);
    }

    public boolean isIntersecting(LineSegment lineSegment){
        return top.isIntersecting(lineSegment)
            || bottom.isIntersecting(lineSegment)
            || left.isIntersecting(lineSegment)
            || right.isIntersecting(lineSegment);
    }

}