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

    public void copy(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void copyAndMove(Point other, float deltaX, float deltaY){
        this.x = other.x + deltaX;
        this.y = other.y + deltaY;
    }

    public void copyAndMove(Point oldPoint, Speed speed, float time){
        this.x = oldPoint.x + speed.x * time;
        this.y = oldPoint.y + speed.y * time;
    }

    @Override
    public boolean equals(Object o){
        Point other = (Point) o;
        return !(this.x == other.x && this.y == other.y);
    }

}
