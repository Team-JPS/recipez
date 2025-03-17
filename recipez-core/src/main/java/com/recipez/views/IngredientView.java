package com.recipez.views;

import com.recipez.util.GlobalValues;
import com.recipez.views.view_models.IngredientViewModel;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

//This is a single Ingredient View, multiple of these will be stacked into VBox inside of CreateRecipeView.java
public class IngredientView extends HBox{
    // 6 labels will be swapped out for six other editable elements
    private Label lblIngredientName, lblQuantity, lblVolume, lblUnitsOfVolume, lblWeight, lblUnitsOfWeight;
    // editable elements to be used when in editable state
    private ChoiceBox<String> cboxQuantity, cboxVolume, cboxUnitsOfVolume, cboxUnitsOfWeight, cboxWeight;
    private TextField tfIngredientNameInput;
    // button to toggle editable state;
    private Button toggleEditButton;


    private final IngredientViewModel ingredientViewModel = new IngredientViewModel();  
    
    public IngredientView(String ingredientName, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight) {
        
        //Initialize elements for the Ingredient View
        createIngredientView(ingredientName, quantity, volume, unitOfVolume, weight, unitOfWeight);
        createIngredientViewEditable(ingredientName, quantity, volume, unitOfVolume, weight, unitOfWeight);
        
        //default view is ingredient without edit
        populateIngredientView();
               
    }

    // Initializes elements for the ingredient View
    public void createIngredientView(String ingredientName, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight){
        this.lblIngredientName = new Label(ingredientName);
        this.lblQuantity = new Label(quantity);
        this.lblVolume = new Label(volume);
        this.lblUnitsOfVolume = new Label(unitOfVolume);
        this.lblWeight = new Label(weight);
        this.lblUnitsOfWeight = new Label(unitOfWeight);
        this.toggleEditButton = new Button("+");
        this.toggleEditButton.setFont(GlobalValues.SMALL_FONT);
    } 

    // Initializes elements for the ingredient View Editable
    public void createIngredientViewEditable(String ingredientName, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight) {
        this.tfIngredientNameInput = new TextField(ingredientName);
        this.cboxQuantity = new ChoiceBox<>();
        this.cboxVolume = new ChoiceBox<>();
        this.cboxUnitsOfVolume = new ChoiceBox<>();
        this.cboxWeight = new ChoiceBox<>();
        this.cboxUnitsOfWeight = new ChoiceBox<>();
        this.toggleEditButton = new Button("+");
        this.toggleEditButton.setFont(GlobalValues.SMALL_FONT);
    }

    public void populateIngredientView(){        
        this.getChildren().clear();
        this.getChildren().addAll(this.lblIngredientName, this.lblVolume, this.lblUnitsOfVolume, this.toggleEditButton);
        this.toggleEditButton.setOnAction(e -> toggleEditableView(false));
    }

    public void populateIngredientsViewEditable(){
        this.getChildren().clear();
        this.getChildren().addAll(this.tfIngredientNameInput, this.cboxVolume, this.cboxUnitsOfVolume, this.toggleEditButton);
        this.toggleEditButton.setOnAction(e -> toggleEditableView(true));
    }

    //bad way to toggle view of editable state. True Editable, False Viewable 
    public void toggleEditableView(boolean toggle){
        //if true show view only
        if(toggle){
            populateIngredientView();
        }else{
            populateIngredientsViewEditable();
        }
    }



}
