package de.openhpi.squash.model;

public abstract class IPositionableRectangle extends IRectangle {
    public Point topLeft     = new Point(0,0);
    public Point topRight    = new Point(0,0);
    public Point bottomRight = new Point(0,0);
    public Point bottomLeft  = new Point(0,0);
    protected Point position = topLeft; // points to the topLeft corner
    public Point center      = new Point(0,0);
    public Point[] corners = new Point[5];
    public LineSegment top    = new LineSegment(topLeft,    topRight);
    public LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
    public LineSegment left   = new LineSegment(topLeft,    bottomLeft);
    public LineSegment right  = new LineSegment(topRight,   bottomRight);
    public LineSegment[] sides = new LineSegment[4];

    public IPositionableRectangle(float width, float height, float posX, float posY){
        super(width, height);
        this.position.set(posX, posY);
        this.setCorners();
        corners[0] = center;
        // clockwise arrangement of corners
        corners[1] = topLeft;
        corners[2] = topRight;
        corners[3] = bottomRight;
        corners[4] = bottomLeft;
        // clockwise arrangement of sides
        sides[0] = top;
        sides[1] = right;
        sides[2] = bottom;
        sides[3] = left;
    }
    
    protected void setCenter(){
        center.copyAndMove(this.position, +super.width/2.0f, +super.height/2.0f);
    }

    protected void setCorners(){
        topRight.copyAndMove(this.position, +super.width, 0);
        bottomLeft.copyAndMove(this.position, 0, +super.height);
        bottomRight.copyAndMove(this.position, +super.width, +super.height);
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