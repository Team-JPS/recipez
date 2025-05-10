package com.recipez.views;

import com.recipez.util.GlobalValues;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class AddIngredientView extends HBox{
    private ChoiceBox<String> cboxQuantity, cboxVolume, cboxUnitsOfVolume, cboxWeight, cboxUnitsOfWeight;
    private TextField tfIngredientNameInput;
    // private Button btnRemoveIngredient;

    public AddIngredientView() {
        this.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);
        // this.setMinWidth();
        HBox.setHgrow(this, Priority.ALWAYS);
        createAddIngredientView();
        
       
    }

    public void createAddIngredientView(){
        this.cboxQuantity = new ChoiceBox<>();
        this.cboxVolume = new ChoiceBox<>();
        this.cboxUnitsOfVolume = new ChoiceBox<>();
        this.cboxWeight = new ChoiceBox<>();
        this.cboxUnitsOfWeight = new ChoiceBox<>();
        this.tfIngredientNameInput = new TextField("");
    

        this.tfIngredientNameInput.setFont(GlobalValues.SMALL_FONT);
        this.cboxVolume.setStyle(GlobalValues.SMALL_FONT_SIZE_STRING+GlobalValues.SMALL_FONT_FAMILY_STRING);
    
        this.cboxUnitsOfVolume.setStyle(GlobalValues.SMALL_FONT_SIZE_STRING+GlobalValues.SMALL_FONT_FAMILY_STRING);
    

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
