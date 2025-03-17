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

    public StringProperty getIngredientNameStringProperty(){
        return this.ingredientNameStringProperty;
    }

    public StringProperty getIngredientQuantityStringProperty(){
        return this.ingredientQuantityStringProperty;
    }

    public StringProperty getIngredientVolumeStringProperty(){
        return this.ingredientVolumeStringProperty;
    }

    public StringProperty getIngredientUnitsOfVolumeStringProperty(){
        return this.ingredientUnitsOfVolumeStringProperty;
    }

    public StringProperty getIngredientWeightStringProperty(){
        return this.ingredientWeightStringProperty;
    }

    public StringProperty getIngredientUnitsOfWeightStringProperty(){
        return this.ingredientUnitsOfWeightStringProperty;
    }

    // public void setIngredient
  
}
