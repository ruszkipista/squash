package de.openhpi.squash.view;

import de.openhpi.squash.common.Display;

public class RectangleView extends IDrawable {
    private float width;
    private float height;
    private float xPos;
    private float yPos;

    public RectangleView(int color){
        super.color = color;
    }

    public void set(float width, float height, float xPos, float yPos, int color){
        this.width = Math.round(width);
        this.height = Math.round(height);
        this.xPos = Math.round(xPos);
        this.yPos = Math.round(yPos);
        super.color = color;
    }

    public void draw(Display display){
        display.fill(display.color(super.color));
        display.rect(this.xPos, this.yPos, this.width, this.height);
    }

}
