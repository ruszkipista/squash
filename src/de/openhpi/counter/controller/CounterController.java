package de.openhpi.counter.controller;

import de.openhpi.counter.common.Observer;
import de.openhpi.counter.model.CounterModel;
import de.openhpi.counter.view.CounterView;

public class CounterController implements Observer{
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

	// process messages
	@Override
	public void update(String message){
		switch (message){
			case "View.SetUpReady":
				this.counterView.update(this.counterModel.getCount());
				break;
			case "View.MouseClicked": 
				this.counterModel.increment();
				break;
			case "Model.CounterIncremented":
			case "Model.CounterDecremented":
				this.counterView.update(this.counterModel.getCount());
				break;
		}
	}	
}