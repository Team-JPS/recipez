package com.recipez.models;

import java.util.ArrayList;

import com.recipez.util.CurrentUpdate;
import com.recipez.util.Observer;
import com.recipez.util.Subject;

public class ObserverModel implements Subject{
    private CurrentUpdate currentUpdate;
    private ArrayList<Observer> listOfObservers; 

    public ObserverModel() {
        this.currentUpdate = CurrentUpdate.NONE;
        this.listOfObservers = new ArrayList<Observer>();      
    }

    public void registerObserver(Observer observer) {
        if(!this.listOfObservers.contains(observer)){
            this.listOfObservers.add(observer);
        }        
    }

    public void removeObserver(Observer observer) {
        this.listOfObservers.remove(observer);
    }

    public void setUpdate(CurrentUpdate update) {
        if(update != this.currentUpdate){
            this.currentUpdate = update;
            notifyObservers();
        }
    }

    public CurrentUpdate getCurrentUpdate(){
        return this.currentUpdate;
    }

    public void notifyObservers(){
        System.out.println("Making it to notifyObservers");
        for (Observer observer : this.listOfObservers){
            observer.update(this.currentUpdate);
        }
    }

}
