package de.openhpi.squash.model;

public class Point {
    public float x;
    public float y;

    public Point(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void move(Point speed, float time){
        this.x += speed.x * time;
        this.y += speed.y * time;
    }

}
