package de.openhpi.squash.view;

import de.openhpi.squash.common.Display;

public class CounterView extends IDrawable {
    private int number;
    private float xPos;
    private float yPos;

    public CounterView(float xPos, float yPos, int color){
        this.xPos = xPos;
        this.yPos = yPos;
        super.color = color;
    }

    public void set(int number, int color){
        this.number = number;
        super.color = color;
    }

    public void draw(Display display){
        display.fill(display.color(super.color));
        display.text(number+"", xPos, yPos);
    }
}
