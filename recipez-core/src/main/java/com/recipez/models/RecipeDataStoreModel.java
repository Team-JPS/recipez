package com.recipez.models;

import java.util.ArrayList;

import com.recipez.util.CurrentUpdate;
import com.recipez.util.Observer;

public class RecipeDataStoreModel {
    private CurrentUpdate currentUpdate;
    private ArrayList<Observer> listOfObservers; 

    public RecipeDataStoreModel() {
        this.currentUpdate = CurrentUpdate.NONE;
        this.listOfObservers = new ArrayList<Observer>();      
    }

    public void addObserver(Observer observer) {
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

    public void notifyObservers(){
        for (Observer observer : this.listOfObservers){
            observer.update(this.currentUpdate);
        }
    }

}
