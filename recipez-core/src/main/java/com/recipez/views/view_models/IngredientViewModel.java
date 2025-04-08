package com.recipez.views.view_models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IngredientViewModel {
    private final StringProperty ingredientNameStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientQuantityStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientVolumeStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientUnitsOfVolumeStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientWeightStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientUnitsOfWeightStringProperty = new SimpleStringProperty();

    private final ObservableList<String> ingredientVolumeCheckBoxProperty =  FXCollections.observableArrayList();
    private final ObservableList<String> ingredientUnitsOfVolumeCheckBoxProperty = FXCollections.observableArrayList();

    public ObservableList<String> ingredientVolumeCheckBoxProperty(){
        return this.ingredientVolumeCheckBoxProperty;
    }

    public ObservableList<String> ingredientUnitsOfVolumeCheckBoxProperty(){
        return this.ingredientUnitsOfVolumeCheckBoxProperty;
    }

    // public IngredientViewModel(){
    //     this.ingredientNameStringProperty.set("");
    //     this.ingredientQuantityStringProperty.set("");
    //     this.ingredientVolumeStringProperty.set("");
    //     this.ingredientUnitsOfVolumeStringProperty.set("");
    //     this.ingredientWeightStringProperty.set("");
    //     this.ingredientUnitsOfWeightStringProperty.set("");
    // }



    public StringProperty ingredientNameStringProperty(){
        return this.ingredientNameStringProperty;
    }

    public StringProperty ingredientQuantityStringProperty(){
        return this.ingredientQuantityStringProperty;
    }

    public StringProperty ingredientVolumeStringProperty(){
        return this.ingredientVolumeStringProperty;
    }

    public StringProperty ingredientUnitsOfVolumeStringProperty(){
        return this.ingredientUnitsOfVolumeStringProperty;
    }

    public StringProperty ingredientWeightStringProperty(){
        return this.ingredientWeightStringProperty;
    }

    public StringProperty ingredientUnitsOfWeightStringProperty(){
        return this.ingredientUnitsOfWeightStringProperty;
    }

    /*Getters and Setters*/

    public String getIngredientName(){
        return this.ingredientNameStringProperty.get();
    }

    public void setIngredientName(String name){
        this.ingredientNameStringProperty.set(name);
    }

    public String getIngredientVolume(){
        return this.ingredientVolumeStringProperty.get();
    }

    public void setIngredientVolume(String volume){
        this.ingredientVolumeStringProperty.set(volume);
    }

    public String getIngredientUnitsOfVolume(){
        return this.ingredientUnitsOfVolumeStringProperty.get();
    }

    public void setIngredientUnitsOfVolume(String unitsOfVolume){
        this.ingredientUnitsOfVolumeStringProperty.set(unitsOfVolume);
    }

    
}
