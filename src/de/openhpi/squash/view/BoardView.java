package de.openhpi.squash.view;

import de.openhpi.squash.common.Display;

public class BoardView extends IDrawable{

	public BoardView(int color){
        super.color = color;
	}
	
    @Override
    public void draw(Display display){
        display.background(display.color(super.color));
    }
}
