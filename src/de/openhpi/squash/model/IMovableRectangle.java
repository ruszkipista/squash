package de.openhpi.squash.model;

public abstract class IMovableRectangle extends IPositionableRectangle {
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed(0,0);
    private Point newPosition = new Point(0,0);

    public IMovableRectangle(float width,  float height,
                             float posX,   float posY,
                             float speedX, float speedY){
        super(width, height, posX, posY);
        this.distancePerSecond.set(speedX, speedY);
    }
    
    public void prepareMove(float lapsedTimeInSecond){
        this.newPosition.copyAndMove(super.position, distancePerSecond, lapsedTimeInSecond);
        super.setCorners(newPosition);
    }

    public boolean finalizeMove() {
        this.modelChanged = super.position.equals(this.newPosition);
        super.position.copy(this.newPosition);
        return this.modelChanged;
    }

    public Point getNewPosition(){
        return this.newPosition;
    }
    public void setNewPositionX(float x){
        this.newPosition.set(x, this.newPosition.y);
    }
    public void setNewPositionY(float y){
        this.newPosition.set(this.newPosition.x, y);
    }

    public Speed getDistancePerSecond(){
        return this.distancePerSecond;
    }

    public void reverseSpeedX(){
        this.distancePerSecond.reverseX();
    }

    public void reverseSpeedY(){
        this.distancePerSecond.reverseY();
    }

    public void setDistancePerSecond(float x, float y){
        this.distancePerSecond.set(x,y);
    }

    public void changeDistancePerSecond(float x, float y){
        this.distancePerSecond.change(x,y);
    }

}
