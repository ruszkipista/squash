package de.openhpi.squash.model;

public class Point {
    public float x;
    public float y;
    private Point other;  // enforce variable reuse in 'equals'

    public Point(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void copy(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void copyAndMove(Point other, float deltaX, float deltaY){
        this.x = other.x + deltaX;
        this.y = other.y + deltaY;
    }

    public void copyAndMove(Point other, Speed speed, float time){
        this.x = other.x + speed.x * time;
        this.y = other.y + speed.y * time;
    }

    @Override
    public boolean equals(Object o){
        this.other = (Point) o;
        return this.x == this.other.x && this.y == this.other.y;
    }

}
