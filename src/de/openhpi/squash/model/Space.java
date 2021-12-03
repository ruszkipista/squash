package de.openhpi.squash.model;

public class Space {
    private double width;
	private double height;

    public Space(double width, double height){
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

}
