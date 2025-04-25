package com.recipez.views;

import com.recipez.util.GlobalValues;
import com.recipez.views.view_models.IngredientViewModel;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//This is a single Ingredient View, multiple of these will be stacked into VBox inside of CreateRecipeView.java
public class IngredientView extends HBox {
    
    // 6 labels will be swapped out for six other editable elements
    private Label lblIngredientName, lblQuantity, lblVolume, lblUnitsOfVolume, lblWeight, lblUnitsOfWeight;

    // editable elements to be used when in editable state
    private ChoiceBox<String> cboxQuantity, cboxVolume, cboxUnitsOfVolume, cboxUnitsOfWeight, cboxWeight;
    private TextField tfIngredientNameInput;

    // button to toggle editable state;
    private Button btnToggleEdit, btnDeleteIngredientView;

    private final IngredientViewModel ingredientViewModel = new IngredientViewModel();  
    
    public IngredientView(String ingredientName, String quantity, String volume, String unitsOfVolume, String weight, String unitOfWeight) {        
        //Initialize elements for the Ingredient View
        createIngredientView(ingredientName, quantity, volume, unitsOfVolume, weight, unitOfWeight);
        createIngredientViewEditable(ingredientName, quantity, volume, unitsOfVolume, weight, unitOfWeight);        
        //default view is ingredient without edit
        populateIngredientView();
        bindViewModel();  
        // HBox.setHgrow(this, Priority.ALWAYS);             
    }

    public void bindViewModel(){
        this.lblIngredientName.textProperty().bindBidirectional(ingredientViewModel.ingredientNameStringProperty());       
        this.tfIngredientNameInput.textProperty().bindBidirectional(ingredientViewModel.ingredientNameStringProperty());

        Bindings.bindContentBidirectional(ingredientViewModel.ingredientVolumeCheckBoxProperty(), this.cboxVolume.getItems());
        Bindings.bindContentBidirectional(ingredientViewModel.ingredientUnitsOfVolumeCheckBoxProperty(), this.cboxUnitsOfVolume.getItems());

        this.cboxVolume.valueProperty().bindBidirectional(ingredientViewModel.ingredientVolumeStringProperty());
        this.lblVolume.textProperty().bindBidirectional(ingredientViewModel.ingredientVolumeStringProperty());
    
        this.cboxUnitsOfVolume.valueProperty().bindBidirectional(ingredientViewModel.ingredientUnitsOfVolumeStringProperty());
        this.lblUnitsOfVolume.textProperty().bindBidirectional(ingredientViewModel.ingredientUnitsOfVolumeStringProperty());

    }

    // Initializes elements for the ingredient View
    public void createIngredientView(String ingredientName, String quantity, String volume, String unitsOfVolume, String weight, String unitOfWeight){
        // System.out.println("IngredientView.creatIngredientView() ingredient name: "+ ingredientName);
        this.lblIngredientName = new Label("");
        this.lblQuantity = new Label(quantity);
        this.lblVolume = new Label(volume);
        this.lblUnitsOfVolume = new Label(unitsOfVolume);

        // These two buttons are unique as they can go in either Editable or View, depending in which is currently user facing.
        // intialized here as this is the default user view.
        this.btnToggleEdit = new Button("+");
        this.btnDeleteIngredientView = new Button("x");
        
        //not in use at the moment
        this.lblWeight = new Label(weight);
        this.lblUnitsOfWeight = new Label(unitOfWeight);       
       

        this.ingredientViewModel.setIngredientVolume(volume);
        this.ingredientViewModel.setIngredientUnitsOfVolume(unitsOfVolume);
        // Ingredient name needs to be set in the viewModel before the binding works.
        this.ingredientViewModel.setIngredientName(ingredientName);
       
        //need to add padding to these elements so they match up with the textfield and choiceboxes in the editable view
        this.lblIngredientName.setMinWidth(200);
        this.lblIngredientName.setStyle("-fx-padding: 6 0 0 11;"+GlobalValues.COLOR_TEST_FORMATTING_THREE);

        this.lblIngredientName.setFont(GlobalValues.SMALL_FONT);
        this.lblVolume.setMinWidth(50);
        this.lblUnitsOfVolume.setMinWidth(50);
        this.lblVolume.setFont(GlobalValues.SMALL_FONT);
        this.lblUnitsOfVolume.setFont(GlobalValues.SMALL_FONT);
        this.btnToggleEdit.setFont(GlobalValues.SMALL_FONT);
        this.btnDeleteIngredientView.setFont(GlobalValues.SMALL_FONT);

    } 

    // Initializes elements for the ingredient View Editable
    public void createIngredientViewEditable(String ingredientName, String quantity, String volume, String unitsOfVolume, String weight, String unitOfWeight) {
        this.tfIngredientNameInput = new TextField("");
        this.cboxQuantity = new ChoiceBox<>();
        this.cboxVolume = new ChoiceBox<>();
        this.cboxUnitsOfVolume = new ChoiceBox<>();

        //not in use at the moment
        this.cboxWeight = new ChoiceBox<>();
        this.cboxUnitsOfWeight = new ChoiceBox<>();
        
        // this.toggleEditButton = new Button("-");

        this.cboxVolume.getItems().addAll(GlobalValues.VOLUMEVALUES);
        this.cboxVolume.setValue(volume);
        this.cboxUnitsOfVolume.getItems().addAll(GlobalValues.UNITSOFVOLUMEVALUES);
        this.cboxUnitsOfVolume.setValue(unitsOfVolume);
        // If this is the view that is shown first this may need to be set first too.
        // this.ingredientViewModel.setIngredientName(ingredientName);
        
        this.tfIngredientNameInput.setMinWidth(200);
        this.tfIngredientNameInput.setFont(GlobalValues.SMALL_FONT);
        this.cboxVolume.setMinWidth(50);
        this.cboxUnitsOfVolume.setMinWidth(50);
        this.cboxVolume.setStyle(GlobalValues.SMALL_FONT_SIZE_STRING + GlobalValues.SMALL_FONT_FAMILY_STRING);
        this.cboxUnitsOfVolume.setStyle(GlobalValues.SMALL_FONT_SIZE_STRING + GlobalValues.SMALL_FONT_FAMILY_STRING);
        this.btnToggleEdit.setFont(GlobalValues.SMALL_FONT);
    }

    public void populateIngredientView(){     
        // System.out.println("IngredientView.populateIngredientView() ingredient name: "+ this.lblIngredientName.getText());  
        this.btnToggleEdit.setText("+");
        this.getChildren().clear();
        this.getChildren().addAll(this.lblIngredientName, this.lblVolume, this.lblUnitsOfVolume, this.btnToggleEdit);
        // System.out.println("(2) IngredientView.populateIngredientView() ingredient name: "+ this.lblIngredientName.getText()+"\n"); 
        this.btnToggleEdit.setOnAction(e -> toggleEditableView(false));
    }

    public void populateIngredientsViewEditable(){
        // System.out.println("IngredientView.populateIngredientEditable() ingredient name: "+ this.tfIngredientNameInput.getText()); 
        this.btnToggleEdit.setText("-");
        this.getChildren().clear();
        this.getChildren().addAll(this.tfIngredientNameInput, this.cboxVolume, this.cboxUnitsOfVolume, this.btnToggleEdit);
        // System.out.println("(2)IngredientView.populateIngredientEditable() ingredient name: "+ this.tfIngredientNameInput.getText()+"\n"); 
        this.btnToggleEdit.setOnAction(e -> toggleEditableView(true));
    }

    //bad way to toggle view of editable state? True Editable, False Viewable 
    public void toggleEditableView(boolean toggle){
        // if true show viewable
        if(toggle){
            populateIngredientView();
        // else false show editable
        }else{
            populateIngredientsViewEditable();
        }
    }


    // public void deleteIngredientView(IngredientView ingredientView){
    //     ingredientViewModel.deleteIngredientView(ingredientView);
    // }

    public String getIngredientName(){
        return ingredientViewModel.getIngredientName();
    }

    public String getIngredientVolume(){
        return ingredientViewModel.getIngredientVolume();
    }

    public String getIngredientUnitsOfVolume(){
        return ingredientViewModel.getIngredientUnitsOfVolume();
    }

}
