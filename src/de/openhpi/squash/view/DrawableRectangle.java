package de.openhpi.squash.view;

public class DrawableRectangle extends Drawable {
    float xPos;
    float yPos;
    float width;
    float height;

    public void set(float xPos, float yPos, float width, float height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void draw(SquashView display){
        display.rect(this.xPos, this.yPos, this.width, this.height);
    }
}
