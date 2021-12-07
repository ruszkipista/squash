package de.openhpi.squash.model;

public abstract class IMovableRectangle extends IPositionableRectangle {
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed(0,0);
    private Point newTopLeft     = new Point(0,0);
    private Point newTopRight    = new Point(0,0);
    private Point newBottomRight = new Point(0,0);
    private Point newBottomLeft  = new Point(0,0);
    private Point newPosition    = newTopLeft;
    public Point newCenter       = new Point(0,0);
    public Point[] newCorners    = new Point[5];

    public IMovableRectangle(float width,  float height,
                             float posX,   float posY,
                             float speedX, float speedY){
        super(width, height, posX, posY);
        this.distancePerSecond.set(speedX, speedY);
        newCorners[0] = newCenter;
        // clockwise arrangement of new corners
        newCorners[1] = newTopLeft;
        newCorners[2] = newTopRight;
        newCorners[3] = newBottomRight;
        newCorners[4] = newBottomLeft;
    }
    
    public void prepareMove(float lapsedTimeInSecond){
        // calculate new corners
        this.newTopLeft.copyAndMove(super.topLeft, distancePerSecond, lapsedTimeInSecond);
        this.newTopRight.copyAndMove(this.newTopLeft, +super.width, 0);
        this.newBottomRight.copyAndMove(this.newTopLeft, +super.width, +super.height);
        this.newBottomLeft.copyAndMove(this.newTopLeft, 0, +super.height);
    }

    protected void setNewCenter(){
        newCenter.copyAndMove(this.newTopLeft, +super.width/2.0f, +super.height/2.0f);
    }

    public boolean finalizeMove() {
        this.modelChanged = ! super.position.equals(this.newPosition);
        // overwrite position with new
        super.position.copy(this.newPosition);
        return this.modelChanged;
    }

    public Point getNewPosition(){
        return this.newPosition;
    }

    public Speed getDistancePerSecond(){
        return this.distancePerSecond;
    }

    public void setDistancePerSecond(float x, float y){
        this.distancePerSecond.set(x,y);
    }

    public void changeDistancePerSecond(float x, float y){
        this.distancePerSecond.change(x,y);
    }

}
