package de.openhpi.squash.model;

public abstract class IPositionableRectangle {
    public float width;
    public float height;
    public boolean justCollided;
    // clockwise arrangement of corners    
    protected Point[] corners = {new Point(),new Point(),new Point(),new Point()};

    public IPositionableRectangle(float width, float height, float posX, float posY){
        this.width = width;
        this.height = height;
        setPosition(posX, posY);
        setCorners(this.corners);
    }
    
    protected void setCorners(Point[] corners){
        // relative to corner[0]
        corners[1].copyAndMove(corners[0], +this.width,            0);
        corners[2].copyAndMove(corners[0], +this.width, +this.height);
        corners[3].copyAndMove(corners[0],           0, +this.height);
    }

    public float getPositionX(){
        return this.corners[0].x;
    }
    public float getPositionY(){
        return this.corners[0].y;
    }

    public void setPosition(float x, float y){
        this.corners[0].set(x,y);
    }

    public IPositionableRectangle getPositionableRectangle(){
        return this;
    }
}