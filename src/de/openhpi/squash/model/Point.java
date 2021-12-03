package de.openhpi.squash.model;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        set(x, y);
    }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void move(Point speed, double time){
        this.x += speed.x * time;
        this.y += speed.y * time;
    }

}
