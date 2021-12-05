package de.openhpi.squash.model;

public abstract class IPositionableRectangle extends IRectangle {
    protected Point oldPposition = new Point(0,0);

    private Point topLeft     = new Point(0,0);
    private Point topRight    = new Point(0,0);
    private Point bottomLeft  = new Point(0,0);
    private Point bottomRight = new Point(0,0);
    private LineSegment top    = new LineSegment(topLeft,    topRight);
    private LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    private LineSegment left   = new LineSegment(topLeft,    bottomLeft);
    private LineSegment right  = new LineSegment(topRight,   bottomRight);

    public IPositionableRectangle(float width, float height){
        super(width, height);
        this.setCorners(this.oldPposition);
    }
    
    protected void setCorners(Point newPosition){
        topLeft.copyAndMove(newPosition, 0, 0);
        topRight.copyAndMove(newPosition, +super.width, 0);
        bottomLeft.copyAndMove(newPosition, 0, +super.height);
        bottomRight.copyAndMove(newPosition, +super.width, +super.height);
    }

    public Point getPosition(){
        return this.oldPposition;
    }
    public void setPosition(float x, float y){
        this.oldPposition.set(x,y);
    }

    public boolean isIntersecting(LineSegment lineSegment){
        return top.isIntersecting(lineSegment)
            || bottom.isIntersecting(lineSegment)
            || left.isIntersecting(lineSegment)
            || right.isIntersecting(lineSegment);
    }

}