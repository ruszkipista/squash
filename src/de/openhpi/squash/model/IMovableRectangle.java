package de.openhpi.squash.model;

public abstract class IMovableRectangle extends IPositionableRectangle {
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed(0,0);
    private Point newPosition = new Point(0,0);

    public IMovableRectangle(float width, float height){
        super(width, height);
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
