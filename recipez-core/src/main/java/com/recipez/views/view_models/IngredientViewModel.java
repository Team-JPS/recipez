package com.recipez.views.view_models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IngredientViewModel {
    private final StringProperty ingredientNameStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientQuantityStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientVolumeStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientUnitsOfVolumeStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientWeightStringProperty = new SimpleStringProperty();
    private final StringProperty ingredientUnitsOfWeightStringProperty = new SimpleStringProperty();

    public IngredientViewModel(){
        this.ingredientNameStringProperty.set("");
        this.ingredientQuantityStringProperty.set("");
        this.ingredientVolumeStringProperty.set("");
        this.ingredientUnitsOfVolumeStringProperty.set("");
        this.ingredientWeightStringProperty.set("");
        this.ingredientUnitsOfWeightStringProperty.set("");
    }

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

    public String getIngredientName(){
        return this.ingredientNameStringProperty.get();
    }

    public void setIngredientName(String name){
        this.ingredientNameStringProperty.set(name);
    }

}
