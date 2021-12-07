package de.openhpi.squash.model;

public class Speed {
    public float x;
    public float y;

    public Speed(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void change(float x, float y){
        this.x *= x;
        this.y *= y;        
    }

    public void negateX(){
        this.x = -this.x;
    }
    public void negateY(){
        this.y *= -this.y;
    }
}
