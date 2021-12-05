package de.openhpi.squash.model;

public abstract class MovableRectangle extends PositionableRectangle {
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed(0,0);
    private Point newPosition = new Point(0,0);

    public MovableRectangle(float width, float height){
        super(width, height);
    }
    
    public void prepareMove(float lapsedTimeInSecond){
        newPosition.copyAndMove(super.oldPposition, distancePerSecond, lapsedTimeInSecond);
        super.setCorners(newPosition);
    }

    public boolean finalizeMove() {
        modelChanged = super.oldPposition.equals(newPosition);
        oldPposition.copy(this.newPosition);
        return modelChanged;
    }

    public void setDistancePerSecond(float x, float y){
        this.distancePerSecond.set(x,y);
    }

    public void changeSpeed(float x, float y){
        distancePerSecond.change(x,y);
    }

}
