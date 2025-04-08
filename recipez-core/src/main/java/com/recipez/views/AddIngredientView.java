package com.recipez.views;

import com.recipez.util.GlobalValues;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AddIngredientView extends HBox{
    private ChoiceBox<String> cboxQuantity, cboxVolume, cboxUnitsOfVolume, cboxWeight, cboxUnitsOfWeight;
    private TextField tfIngredientNameInput;
    // private Button btnRemoveIngredient;

    public AddIngredientView() {
         createAddIngredientView();
    }

    public void createAddIngredientView(){
        this.cboxQuantity = new ChoiceBox<>();
        this.cboxVolume = new ChoiceBox<>();
        this.cboxUnitsOfVolume = new ChoiceBox<>();
        this.cboxWeight = new ChoiceBox<>();
        this.cboxUnitsOfWeight = new ChoiceBox<>();
        this.tfIngredientNameInput = new TextField("");
     
        this.cboxVolume.getItems().setAll(GlobalValues.VOLUMEVALUES);
        this.cboxUnitsOfVolume.getItems().setAll(GlobalValues.UNITSOFVOLUMEVALUES);

        this.getChildren().addAll(tfIngredientNameInput, cboxVolume, cboxUnitsOfVolume);
    }

    public String getIngredientName(){
        return this.tfIngredientNameInput.getText();
    }  

    public String getIngredientVolume(){
        return this.cboxVolume.getValue();
    }

    public String getIngredientUnitsOfVolume(){
        return this.cboxUnitsOfVolume.getValue();
    }
  
}
