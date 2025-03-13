package com.recipez.views;

import com.recipez.views.view_models.RecipeViewModel;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.recipez.models.RecipeDataStoreModel;
import com.recipez.models.POJO.Ingredient;
import com.recipez.util.CurrentUpdate;
import com.recipez.util.GlobalValues;
import com.recipez.util.Observer;
import com.recipez.util.Subject;
import com.recipez.util.Utility;

//extends GridPane, design choice for layout of elements. 
public class CreateRecipeView extends GridPane implements Observer{

    // Add/Edit Recipe Name UI elements
    private Label lblRecipeName, lblUserMessage;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName, btnSaveRecipe;
    private HBox hboxRecipeNameInput, hboxRecipeNameLabel;
    private VBox vboxInputContainer, separatorNameInput, vboxLabelContainer, separatorNameLabel;
    private boolean recipeNameToggle;    
    
    // Add/remove recipe ingredients UI Elements
    private VBox vboxIngredientsList;
    private ScrollPane spaneIngredientsListHolder;

    //Add/remove recipe instructions UI Elements
    private VBox vboxInstructionsList;
    private ScrollPane spaneInstrcutionsListHolder;

    //Data store for creating a recipe... RecipeViewModel has notes on its usage with CreateRecipeView and RecipeView.
    private final RecipeViewModel recipeViewModel = new RecipeViewModel();
    private Subject dataStoreUpdater;


    public CreateRecipeView(Subject dataStoreUpdater){
        this.setVgap(5);
        this.setHgap(5);  
        ColumnConstraints columns = new ColumnConstraints(); 
        columns.setPercentWidth(50);
        this.getColumnConstraints().addAll(columns);   
        dataStoreUpdater.registerObserver(this);
        this.dataStoreUpdater = dataStoreUpdater;
        loadRecipe();
        createRecipeNameView();
        createIngredientsListView();
        createInstructionsListView();
        bindViewModel(); 
    }

    private void createIngredientsListView(){
        this.vboxIngredientsList = new VBox();
        this.spaneIngredientsListHolder = new ScrollPane();
        this.spaneIngredientsListHolder.setFitToHeight(true);
        this.spaneIngredientsListHolder.setPrefViewportHeight(200);
        String[] storage = {"Carrot", "cheese", "Bacon"};
        // ArrayList<Ingredient> loadedFromStorage = new ArrayList<Ingredient>();       
        // for (String ingredient : storage) {
        //     recipeViewModel.addIngredient(new Ingredient(ingredient));
        // }

        for(Ingredient ingredient : recipeViewModel.getIngredients()){
            this.vboxIngredientsList.getChildren().add(new Label(ingredient.getName())); 
        }
        
        this.spaneIngredientsListHolder.setContent(vboxIngredientsList);
        this.add(this.spaneIngredientsListHolder, 0, 1);
        this.vboxIngredientsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);
        
    }
 
    private void createInstructionsListView(){
        this.vboxInstructionsList = new VBox();
        this.spaneInstrcutionsListHolder = new ScrollPane();
        this.spaneInstrcutionsListHolder.setFitToHeight(true);
        this.spaneInstrcutionsListHolder.setPrefViewportHeight(200);
        String[] storage = {"Turn up heat", "Cook the stuff", "let cool and serve"};
        // ArrayList<String> loadedFromStorage = new ArrayList<String>();       
        for (String instruction : storage) {
            recipeViewModel.addInstruction(instruction);
        }
        for(String instruction : recipeViewModel.getInstructions()){
            this.vboxInstructionsList.getChildren().add(new Button(instruction)); 
        }        
        this.spaneInstrcutionsListHolder.setContent(this.vboxInstructionsList);
        this.add(this.spaneInstrcutionsListHolder, 1, 1);
        this.vboxInstructionsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);        
    }

    private void createRecipeNameView(){
        this.recipeNameToggle = true;
        this.lblUserMessage = new Label("Click to rename your recipe!");
        this.tfRecipeName = new TextField("");
        this.lblRecipeName = new Label("");      
                
        this.btnSaveRecipeName = new Button("Save");
        this.btnSaveRecipe = new Button("Save Recipe");

        this.lblUserMessage.setFont(GlobalValues.LARGE_FONT);
        this.lblRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.tfRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.btnSaveRecipeName.setFont(GlobalValues.MEDIUM_FONT);        
        this.btnSaveRecipe.setFont(GlobalValues.MEDIUM_FONT);

        this.setStyle(GlobalValues.COLOR_PRIMARY);

        this.lblRecipeName.setStyle("-fx-padding: 12 8 8 8");
        this.tfRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipe.setAlignment(Pos.CENTER);

        this.lblUserMessage.setOnMouseClicked(this::swapLayer);
        this.lblRecipeName.setOnMouseClicked(this::swapLayer);
        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::saveRecipeName);
        this.btnSaveRecipe.setOnAction(this::saveRecipe);
        
        this.hboxRecipeNameInput = new HBox(); 
        this.hboxRecipeNameLabel = new HBox(); 

        this.vboxInputContainer = new VBox();
        this.vboxLabelContainer = new VBox();      
        this.separatorNameInput = new VBox();
        this.separatorNameLabel = new VBox();

        /*********************************User input screen*********************************/
    
        //TODO: This may be easier to do with a GridPane        
        //Hbox container for textfield for user input   
        this.hboxRecipeNameInput.setSpacing(10);
        this.hboxRecipeNameInput.setAlignment(Pos.CENTER);
        this.hboxRecipeNameInput.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);        
        this.hboxRecipeNameInput.getChildren().add(this.tfRecipeName);        
        this.hboxRecipeNameInput.setPrefWidth(GlobalValues.APP_WIDTH);
        HBox.setHgrow(this.tfRecipeName, Priority.ALWAYS);
        
        //Vbox to vertically align tfRecipeName and btnSaveRecipeName 
        this.separatorNameInput.getChildren().addAll(this.hboxRecipeNameInput, this.btnSaveRecipeName);
        this.separatorNameInput.setAlignment(Pos.TOP_CENTER);
        this.separatorNameInput.setPrefHeight(150);
        this.separatorNameInput.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);
        
        //Vbox to hold Hbox to user input textfield
        this.vboxInputContainer.setPrefHeight(GlobalValues.VIEW_HEIGHT-300);
        this.vboxInputContainer.setAlignment(Pos.CENTER);
        this.vboxInputContainer.setStyle(GlobalValues.COLOR_PRIMARY);
        this.vboxInputContainer.getChildren().add(separatorNameInput);

        /*********************************Input display screen*********************************/

        //Hbox container that displays user input via label             
        // this.hboxRecipeNameLabel.setSpacing(10);
        this.hboxRecipeNameLabel.setAlignment(Pos.CENTER);
        this.hboxRecipeNameLabel.setStyle(GlobalValues.COLOR_PRIMARY);
        this.hboxRecipeNameLabel.getChildren().add(this.lblRecipeName);        
        this.hboxRecipeNameLabel.setPrefWidth(GlobalValues.APP_WIDTH);

        //Vbox to vertically align lblUserMessage and lblRecipeName 
        this.separatorNameLabel.getChildren().addAll(this.hboxRecipeNameLabel, this.lblUserMessage);
        this.separatorNameLabel.setAlignment(Pos.TOP_CENTER);
        this.separatorNameLabel.setPrefHeight(150);        
        this.separatorNameLabel.setStyle(GlobalValues.COLOR_PRIMARY);
        
        //Vbox to hold Hbox user input label     
        this.vboxLabelContainer.setPrefHeight(GlobalValues.VIEW_HEIGHT-300);
        this.vboxLabelContainer.setAlignment(Pos.CENTER);
        this.vboxLabelContainer.setStyle(GlobalValues.COLOR_PRIMARY);
        this.vboxLabelContainer.getChildren().add(separatorNameLabel);

        //UI elements combined for main display of RecipeView              
        this.add(this.vboxLabelContainer, 0, 0);
        this.add(this.vboxInputContainer, 0, 0);
        this.add(this.btnSaveRecipe, 0, 2);
        GridPane.setColumnSpan(btnSaveRecipe, 2);
        GridPane.setColumnSpan(vboxInputContainer, 2);        
        GridPane.setColumnSpan(vboxLabelContainer, 2);
        this.setMinHeight(GlobalValues.VIEW_HEIGHT);
    }

    //SwapLayer being called by a MouseEvent 
    private void swapLayer(MouseEvent event){
        swap();
    }

    //SwapLayer being called by a KeyEvent 
    private void swapLayer(KeyEvent event){
        swap();
    }

    //SwapLayer being called by a ActionEvent
    private void swapLayer(ActionEvent event){
        swap();
    }

    private void swap(){
        if(this.recipeNameToggle){
            Utility.fadeOut(this.vboxInputContainer);
            this.btnSaveRecipeName.setDisable(true);
            this.tfRecipeName.setDisable(true);            
            binarySwap(vboxLabelContainer, vboxInputContainer);
            Utility.fadeIn(this.vboxLabelContainer);
            this.recipeNameToggle = !this.recipeNameToggle;            
        }else{
            Utility.fadeOut(this.vboxLabelContainer);
            this.btnSaveRecipeName.setDisable(false);
            this.tfRecipeName.setDisable(false);            
            binarySwap(vboxInputContainer, vboxLabelContainer);
            Utility.fadeIn(this.vboxInputContainer);
            this.recipeNameToggle = !this.recipeNameToggle;  
        }
    }

    //The fade transition isnt smooth without both being present, hence the binary swap
    private void binarySwap(Node front, Node back){
        for(Node node : this.getChildren()){
            if(GridPane.getColumnIndex(node) == GridPane.getColumnIndex(front) && GridPane.getRowIndex(node) == GridPane.getRowIndex(front)) {               
                this.getChildren().removeAll(front, back);                      
                this.add(back, GridPane.getColumnIndex(node), GridPane.getRowIndex(node));                 
                this.add(front, GridPane.getColumnIndex(node), GridPane.getRowIndex(node));
                break;
            }
        }
    }

    //bind UI input fields to propteries of the recipeViewModel
    private void bindViewModel(){
        this.tfRecipeName.textProperty().bindBidirectional(recipeViewModel.recipeNameProperty());
        this.lblRecipeName.textProperty().bindBidirectional(recipeViewModel.recipeNameProperty()); 
    }    

    // processKeyPres(), and both saveRecipeName() methods needs to be looked at, maybe too obtuse? Streamline this? dont know how at the moment.
    private void processKeyPress(KeyEvent event){
       switch(event.getCode()){
            case ENTER:
                saveRecipeName(event);
                break;
            default:
       } 
    }
    // Saving the name of the recipe to the recipeViewModel, should also save to temp recipe json.
    // Auto save points should be created for temp recipe json. 
    private void saveRecipeName(ActionEvent event){ recipeViewModel.setName(tfRecipeName.getText()); saveTemporaryRecipe(event); swapLayer(event); }
    private void saveRecipeName(KeyEvent event){ recipeViewModel.setName(tfRecipeName.getText()); saveTemporaryRecipe(event); swapLayer(event); }

    // Saves for temporary recipes. The logic is if someone doesnt finish creating a recipe and closes the app, they dont lose their progress.
    private void saveTemporaryRecipe(ActionEvent event) { recipeViewModel.saveTemporaryRecipe();}    
    private void saveTemporaryRecipe(KeyEvent event) { recipeViewModel.saveTemporaryRecipe();}

    // this save should save recipe json file, and delete the tempRecipe json.  
    // This is a terrible way to handle the CurrentUpdate for recipe as this works even if it doesnt save it. need beter logic. for testing only.
    private void saveRecipe(ActionEvent event) { recipeViewModel.saveRecipe(); ((RecipeDataStoreModel)this.dataStoreUpdater).setUpdate(CurrentUpdate.RECIPE);}

    private void loadRecipe(){ 
        recipeViewModel.loadTemporaryRecipe();
        // this.getChildren().clear();
        // createRecipeNameView();
        // createIngredientsListView();
        // createInstructionsListView();
        // bindViewModel();
    }

    public void update(CurrentUpdate update) {
        //For now CreateRecipeView isnt looking for any updates. so it doesnt matter what the current update is.
    }
}
