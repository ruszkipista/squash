package de.openhpi.squash.model;

public class CounterModel implements IFrameable{
    private int count;
    private int newCount;

    public CounterModel(int count){
        this.count = count;
    }

    public void increment(){
        this.newCount = this.count + 1;
    }

    public int getCount(){
        return this.count;
    }

    // Frameable
    @Override
    public void calculateNextFrame(float lapsedTimeInSec){
	}

    // Frameable
	@Override
    public boolean finalizeNextFrame(){
        if (this.count == this.newCount)
            return false;
        else {
            this.count = this.newCount;
            return true;
        }
	}    
}
