package de.openhpi.squash.model;

public abstract class IPositionableRectangle extends IRectangle {
    protected Point topLeft     = new Point(0,0);
    protected Point topRight    = new Point(0,0);
    protected Point bottomRight = new Point(0,0);
    protected Point bottomLeft  = new Point(0,0);
    // clockwise arrangement of corners    
    protected Point[] corners = {topLeft,topRight,bottomRight,bottomLeft};
    private LineSegment top    = new LineSegment(topLeft,    topRight);
    private LineSegment right  = new LineSegment(topRight,   bottomRight);
    private LineSegment bottom = new LineSegment(bottomRight,bottomLeft);
    private LineSegment left   = new LineSegment(bottomLeft, topLeft);
    // clockwise arrangement of sides
    protected LineSegment[] sides = {top,right,bottom,left};
    public int[] sideIntersectCounts = new int[4]; // we deal with rectangles only

    public IPositionableRectangle(float width, float height, float posX, float posY){
        super(width, height);
        this.topLeft.set(posX, posY);
        this.setCorners();
    }
    
    protected void setCorners(){
        topRight.copyAndMove(this.topLeft, +super.width, 0);
        bottomLeft.copyAndMove(this.topLeft, 0, +super.height);
        bottomRight.copyAndMove(this.topLeft, +super.width, +super.height);
    }

    public float getPositionX(){
        return this.topLeft.x;
    }
    public float getPositionY(){
        return this.topLeft.y;
    }

    public void setPosition(float x, float y){
        this.topLeft.set(x,y);
    }

}