package de.openhpi.squash.view;

import de.openhpi.squash.common.Display;

public class BallView extends Drawable {
    private float xPos;
    private float yPos;
    private float side;

    public void set(float side, float xPos, float yPos){
        this.side = side;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Display display){
        display.fill(display.color(display.darkColor));
        display.rect(this.xPos, this.yPos, this.side, this.side);
    }

}
