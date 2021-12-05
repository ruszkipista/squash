package de.openhpi.squash.model;

public abstract class IRectangle {
    public float width;
    public float height;

    public IRectangle(float width, float height){
        this.width = width;
        this.height = height;
    }

}
