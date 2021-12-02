package de.openhpi.counter.controller;

import de.openhpi.counter.common.ModelObserver;
import de.openhpi.counter.common.ViewObserver;
import de.openhpi.counter.model.CounterModel;
import de.openhpi.counter.view.CounterView;

public class CounterController implements ViewObserver, ModelObserver{
	static CounterController instance;
    private CounterView counterView;
	private CounterModel counterModel;
	
	public static void setup(CounterView counterView, CounterModel counterModel) {
		instance = new CounterController(counterView, counterModel);
	}

	private CounterController(CounterView counterView, CounterModel counterModel){
		this.counterView = counterView;
		this.counterView.registerObserver(this);

		this.counterModel = counterModel;
		this.counterModel.registerObserver(this);
	}

	// process message from View
	public void updateFromView(){
		// send message to Model
		this.counterModel.increment();
	}

	// process message from Model
	public void updateFromModel(){
		// send message to View
		this.counterView.update(this.counterModel.getCount());
	}	
}