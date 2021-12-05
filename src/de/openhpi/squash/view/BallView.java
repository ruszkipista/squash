package de.openhpi.squash.view;

import de.openhpi.squash.common.Display;

public class BallView extends IDrawable {
    private float xPos;
    private float yPos;
    private float side;

    public void set(float side, float xPos, float yPos){
        this.side = Math.round(side);
        this.xPos = Math.round(xPos);
        this.yPos = Math.round(yPos);
    }

    public void draw(Display display){
        display.fill(display.color(display.darkColor));
        display.rect(this.xPos, this.yPos, this.side, this.side);
    }

}
