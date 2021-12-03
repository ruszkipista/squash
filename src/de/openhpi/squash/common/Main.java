package de.openhpi.squash.common;

import de.openhpi.squash.view.SquashView;
import processing.core.PApplet;

public class Main {

	public static void main(String[] args) {
        // this call instantiates SquashView and every dependencies
		PApplet.main(new String[]{SquashView.class.getName()});
		
		//To start the program in Fullscreen Mode, use this instead.
        //PApplet.main(new String[]{"--present",TheApp.class.getName()});
	}    
}
