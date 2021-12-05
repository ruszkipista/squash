package de.openhpi.squash.model;

public abstract class IPositionableRectangle extends IRectangle {
    protected Point position = new Point(0,0);

    private Point topLeft     = new Point(0,0);
    private Point topRight    = new Point(0,0);
    private Point bottomLeft  = new Point(0,0);
    private Point bottomRight = new Point(0,0);
    public LineSegment top    = new LineSegment(topLeft,    topRight);
    public LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    public LineSegment left   = new LineSegment(topLeft,    bottomLeft);
    public LineSegment right  = new LineSegment(topRight,   bottomRight);

    public IPositionableRectangle(float width, float height){
        super(width, height);
        this.setCorners(this.position);
    }
    
    protected void setCorners(Point newPosition){
        topLeft.copyAndMove(newPosition, 0, 0);
        topRight.copyAndMove(newPosition, +super.width, 0);
        bottomLeft.copyAndMove(newPosition, 0, +super.height);
        bottomRight.copyAndMove(newPosition, +super.width, +super.height);
    }

    public Point getPosition(){
        return this.position;
    }
    public void setPosition(float x, float y){
        this.position.set(x,y);
    }

    public boolean isIntersecting(LineSegment lineSegment){
        return top.isIntersectingWith(lineSegment)
            || bottom.isIntersectingWith(lineSegment)
            || left.isIntersectingWith(lineSegment)
            || right.isIntersectingWith(lineSegment);
    }

}