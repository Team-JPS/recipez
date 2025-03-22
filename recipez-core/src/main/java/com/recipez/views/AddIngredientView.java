package com.recipez.views;

import com.recipez.util.GlobalValues;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AddIngredientView extends HBox{
    private ChoiceBox<String> cboxQuantity, cboxVolume, cboxUnitsOfVolume, cboxWeight, cboxUnitsOfWeight;
    private TextField tfIngredientNameInput;
    private Button btnAddIngredient;

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
        // this.btnAddIngredient = new Button("+");
        // this.tfIngredientNameInput.setOnAction(this::processKeyPress);
        // this.btnAddIngredient.setOnAction(this::addNewIngredient);
        // this.tfIngredientNameInput.setOnKeyPressed(this::processKeyPress);
       
        // String[] volumeValues = {" ","1","1/2","1/3","1/4","1/8"};
        this.cboxVolume.getItems().setAll(GlobalValues.VOLUMEVALUES);

        this.getChildren().addAll(tfIngredientNameInput, cboxVolume, cboxUnitsOfVolume);
    }

    public String getIngredientName(){
        return this.tfIngredientNameInput.getText();
    }  

    public String getIngredientVolume(){
        return this.cboxVolume.getValue();
    }

    // public void processKeyPress(KeyEvent event){
    //     System.out.println("\nmethod call processKeyPress(KeyEvent event)\n");
    //     switch(event.getCode()){
    //         case ENTER:
    //             addNewIngredient();
    //         break;
    //         default:
    //         break;
    //     }
    // }

    // public Ingredient addNewIngredient() {  
    //     System.out.println("\nmethod call addNewIngredient()\n");      
    //     return new Ingredient(getIngredientName(), this.cboxVolume.getValue());      
    // }
    
    // public Ingredient addNewIngredient(ActionEvent event) {        
    //     System.out.println("\nmethod call addNewIngredient(ActionEvent event)\n");
    //     return new Ingredient(getIngredientName());      
    // }




}
