package com.recipez.views;

import com.recipez.models.POJO.Ingredient;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
        
        this.getChildren().addAll(tfIngredientNameInput, cboxVolume, cboxUnitsOfVolume);
    }

    public String getIngredientName(){
        return this.tfIngredientNameInput.getText();
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

    public Ingredient addNewIngredient() {  
        System.out.println("\nmethod call addNewIngredient()\n");      
        return new Ingredient(getIngredientName());      
    }
    
    // public Ingredient addNewIngredient(ActionEvent event) {        
    //     System.out.println("\nmethod call addNewIngredient(ActionEvent event)\n");
    //     return new Ingredient(getIngredientName());      
    // }




}
