package de.openhpi.counter.common;

import de.openhpi.counter.model.CounterModel;
import de.openhpi.counter.view.CounterView;
import de.openhpi.counter.controller.CounterController;

import processing.core.PApplet;

public class Main {

	public static void main(String[] args) {
        // this call instantiates CounterView
		PApplet.main(new String[]{CounterView.class.getName()});
		
		//To start the program in Fullscreen Mode, use this instead.
        //PApplet.main(new String[]{"--present",TheApp.class.getName()});

        CounterController.setup(CounterView.getInstance(),new CounterModel(0));
	}    
}
