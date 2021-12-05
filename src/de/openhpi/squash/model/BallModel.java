package de.openhpi.squash.model;

public class BallModel extends MovableRectangle{
    public float side;

    public BallModel(float side){
        super(side, side);
        this.side = side;
    }

}
