package com.recipez.views;

import com.recipez.views.view_models.IngredientViewModel;
import com.recipez.views.view_models.RecipeViewModel;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

import com.recipez.models.ObserverModel;
import com.recipez.models.POJO.Ingredient;
import com.recipez.models.POJO.Recipe;
import com.recipez.util.CurrentUpdate;
import com.recipez.util.CustomValidSaveException;
import com.recipez.util.GlobalValues;
import com.recipez.util.Observer;
import com.recipez.util.Subject;
import com.recipez.util.Utility;

//extends GridPane, design choice for layout of elements. 
public class CreateRecipeView extends GridPane implements Observer{

    // Add/Edit Recipe Name UI elements
    private Label lblRecipeName, lblUserMessage;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName, btnSaveRecipe, btnNewRecipe;
    private HBox hboxRecipeNameInput, hboxRecipeNameLabel;
    private VBox vboxInputContainer, separatorNameInput, vboxLabelContainer, separatorNameLabel;
    private boolean recipeNameToggle;    
    
    // Add/remove recipe ingredients UI Elements
    private VBox vboxIngredientsListView, vboxIngredientsList;
    private HBox hboxAddIngredientChoices;
    private ScrollPane spaneIngredientsListHolder;
    private Button btnAddIngredient;
    private TextField tfIngredientName;
    private ChoiceBox<String> cboxVolume, cboxUnitsOfVolume, cboxUnitsOfWeight, cboxWeight;


    //View for adding an ingredient. HBox 
    private AddIngredientView addIngredientView;


    //Add/remove recipe instructions UI Elements
    private VBox vboxInstructionsListView, vboxInstructionsList;
    private HBox hboxAddInstructionOptions;
    private ScrollPane spaneInstructionsListHolder;
    private TextField tfInstruction;
    private Button btnAddInstruction;

    //Data store for creating a recipe... RecipeViewModel has notes on its usage with CreateRecipeView and RecipeView.
    private final RecipeViewModel recipeViewModel = new RecipeViewModel();
    private Subject dataStoreUpdater;

    public CreateRecipeView(Subject dataStoreUpdater){
        this.setVgap(5);
        this.setHgap(5);  
        ColumnConstraints columns = new ColumnConstraints(); 
        columns.setPercentWidth(50);
        this.getColumnConstraints().addAll(columns);   
        // register this class with with the dataStoreUpdater. Allows changing 
        // of CurrentUpdate, an enum, that the other Views also observe. saveRecipe
        // calls setUpdate(CurrentUpdate currentUpdate) if the Recipe saved 
        // successfully. setUpdate() calls notifyObservers() which lets the other
        // Views know a change was made. 
        dataStoreUpdater.registerObserver(this);
        this.dataStoreUpdater = dataStoreUpdater;    
        // the create...() methods here populate the UI for this this View.    
        createRecipeNameView();
        createIngredientsListView();
        createInstructionsListView();
        //loadRecipe() needs to happen after the UI has been created. 
        loadRecipe();
        bindViewModel(); 
    }

    private void createIngredientsListView(){        
        this.vboxIngredientsListView = new VBox();
        this.vboxIngredientsList = new VBox();
        this.addIngredientView = new AddIngredientView();
        this.hboxAddIngredientChoices = new HBox();
        // this.tfIngredientName = new TextField("");
        this.btnAddIngredient = new Button("+");
        // this.cboxUnitsOfVolume = new ChoiceBox<>();
        // this.cboxVolume = new ChoiceBox<>();

        this.spaneIngredientsListHolder = new ScrollPane();
        this.spaneIngredientsListHolder.setFitToHeight(true);
        this.spaneIngredientsListHolder.setPrefViewportHeight(200);
        
        this.btnAddIngredient.setFont(GlobalValues.MEDIUM_FONT);

        this.btnAddIngredient.setOnAction(this::addIngredient);
        
        this.spaneIngredientsListHolder.setContent(vboxIngredientsList);
        this.hboxAddIngredientChoices.getChildren().addAll(this.btnAddIngredient, this.addIngredientView);        
        // this.vboxIngredientsListView.getChildren().addAll(this.spaneIngredientsListHolder, this.hboxAddIngredientChoices);
        this.vboxIngredientsListView.getChildren().addAll(this.spaneIngredientsListHolder, this.hboxAddIngredientChoices);
        this.add(this.vboxIngredientsListView, 0, 1);        
        this.vboxIngredientsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);        
    }

    private void createInstructionsListView(){
        this.vboxInstructionsList = new VBox();
        this.vboxInstructionsListView = new VBox();
        this.hboxAddInstructionOptions = new HBox();
        this.tfInstruction = new TextField("");
    
        this.btnAddInstruction = new Button("+");
        this.spaneInstructionsListHolder = new ScrollPane();
        this.spaneInstructionsListHolder.setFitToHeight(true);
        this.spaneInstructionsListHolder.setPrefViewportHeight(200); 
        
        this.btnAddInstruction.setOnAction(this::addInstruction);        
        
        this.spaneInstructionsListHolder.setContent(this.vboxInstructionsList);
        this.hboxAddInstructionOptions.getChildren().addAll(this.tfInstruction, this.btnAddInstruction);
        this.vboxInstructionsListView.getChildren().addAll(this.spaneInstructionsListHolder, this.hboxAddInstructionOptions);
        this.add(this.vboxInstructionsListView, 1, 1);
        this.vboxInstructionsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);        
    }


    // UI change, 
    // [HBox][Label][Some UI element][/HBox] <--swaps--> [HBox][TextField][Button][/HBox]
    // The 'Some UI element' will be the swap with the save button. A flower or something, some cute image that
    // sits next to the recipe name
    private void createRecipeNameView(){
        this.recipeNameToggle = true;
        this.lblUserMessage = new Label("Click to rename your recipe!");
        this.tfRecipeName = new TextField(" ");
        this.lblRecipeName = new Label("");      
                
        this.btnSaveRecipeName = new Button("Save");
        this.btnSaveRecipe = new Button("Save Recipe");
        this.btnNewRecipe = new Button("New Recipe");

        this.lblUserMessage.setFont(GlobalValues.LARGE_FONT);
        this.lblRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.tfRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.btnSaveRecipeName.setFont(GlobalValues.MEDIUM_FONT);        
        this.btnSaveRecipe.setFont(GlobalValues.MEDIUM_FONT);
        this.btnNewRecipe.setFont(GlobalValues.MEDIUM_FONT);

        this.setStyle(GlobalValues.COLOR_PRIMARY);

        this.lblRecipeName.setStyle("-fx-padding: 12 8 8 8");
        this.tfRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipe.setAlignment(Pos.CENTER);
        this.btnNewRecipe.setAlignment(Pos.CENTER);

        this.lblUserMessage.setOnMouseClicked(e -> this.swapLayer(false));
        this.lblRecipeName.setOnMouseClicked(e -> this.swapLayer(false));
        
        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::saveRecipeName);
        this.btnSaveRecipe.setOnAction(this::saveRecipe);
        this.btnNewRecipe.setOnAction(this::newRecipe);
        
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
        this.add(this.btnNewRecipe, 1, 2);
        GridPane.setColumnSpan(btnSaveRecipe, 2);
        GridPane.setColumnSpan(vboxInputContainer, 2);        
        GridPane.setColumnSpan(vboxLabelContainer, 2);
        this.setMinHeight(GlobalValues.VIEW_HEIGHT);
    }

    private void loadRecipe(){ 
        Recipe recipe = recipeViewModel.loadTemporaryRecipe();
        populateRecipeName(recipe);
        populateIngredients(recipe);
        populateInstructions(recipe);
    }

    private void populateRecipeName(Recipe recipe){
        this.recipeViewModel.setRecipeName(recipe.getRecipeName());
    }

    // WORKING COPY 
    private void populateIngredients(Recipe recipe){
        for(Ingredient ingredient : recipe.getIngredients()){          
            IngredientView ingredientView = new IngredientView(ingredient.getIngredientName(), ingredient.getQuantity(), ingredient.getVolume(), ingredient.getUnitOfVolume(), ingredient.getWeight(), ingredient.getUnitOfWeight());
            this.addIngredient(ingredientView);
        } 
    }

    private void populateInstructions(Recipe recipe){
        for(String text : recipe.getInstructions()){
           this.addInstruction(text);
        }
    }

    //SwapLayer being called by a MouseEvent 
    // private void swapLayer(MouseEvent event){
    //     swap();
    // }

    // //SwapLayer being called by a KeyEvent 
    // private void swapLayer(KeyEvent event){
    //     swap();
    // }

    // //SwapLayer being called by a ActionEvent
    // private void swapLayer(ActionEvent event){
    //     swap();
    // }

    // private void swap(){
    //     if(this.recipeNameToggle){
    //         Utility.fadeOut(this.vboxInputContainer);
    //         this.btnSaveRecipeName.setDisable(true);
    //         this.tfRecipeName.setDisable(true);            
    //         binarySwap(vboxLabelContainer, vboxInputContainer);
    //         Utility.fadeIn(this.vboxLabelContainer);
    //         this.recipeNameToggle = !this.recipeNameToggle;            
    //     }else{
    //         Utility.fadeOut(this.vboxLabelContainer);
    //         this.btnSaveRecipeName.setDisable(false);
    //         this.tfRecipeName.setDisable(false);            
    //         binarySwap(vboxInputContainer, vboxLabelContainer);
    //         Utility.fadeIn(this.vboxInputContainer);
    //         this.recipeNameToggle = !this.recipeNameToggle;  
    //     }
    // }

    private void swapLayer(Boolean recipeNameToggle) {
        if(recipeNameToggle){
            Utility.fadeOut(this.vboxInputContainer);
            this.btnSaveRecipeName.setDisable(true);
            this.tfRecipeName.setDisable(true);            
            binarySwap(vboxLabelContainer, vboxInputContainer);
            Utility.fadeIn(this.vboxLabelContainer);          
        }else{
            Utility.fadeOut(this.vboxLabelContainer);
            this.btnSaveRecipeName.setDisable(false);
            this.tfRecipeName.setDisable(false);            
            binarySwap(vboxInputContainer, vboxLabelContainer);
            Utility.fadeIn(this.vboxInputContainer); 
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
    @SuppressWarnings("unchecked")
    private void bindViewModel(){
        this.tfRecipeName.textProperty().bindBidirectional(recipeViewModel.recipeNameProperty());
        this.lblRecipeName.textProperty().bindBidirectional(recipeViewModel.recipeNameProperty());
        // this binding of recipeIngredientsNodesProperty is how I update the recipeViewModel with ingredients data, the data is in Node form and the contents 
        // of the Nodes will need to be extracted before they can be saved.
        Bindings.bindContentBidirectional(recipeViewModel.recipeIngredientsNodesProperty(), this.vboxIngredientsList.getChildren());
        Bindings.bindContentBidirectional(recipeViewModel.recipeInstructionsNodesProperty(), this.vboxInstructionsList.getChildren());
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
    private void saveRecipeName(ActionEvent event){ recipeViewModel.setRecipeName(tfRecipeName.getText()); saveTemporaryRecipe(event); swapLayer(true); }
    private void saveRecipeName(KeyEvent event){ recipeViewModel.setRecipeName(tfRecipeName.getText()); saveTemporaryRecipe(event); swapLayer(true); }

    // Saves for temporary recipes. The logic is if someone doesnt finish creating a recipe and closes the app, they dont lose their progress.
    private void saveTemporaryRecipe(ActionEvent event) { recipeViewModel.saveTemporaryRecipe();}    
    private void saveTemporaryRecipe(KeyEvent event) { recipeViewModel.saveTemporaryRecipe();}

    // this save should save recipe json file. When recipeBook saves it to the recipe book, it will delete this file as it will now be part of another file, the recipe book.  
        
    // @SuppressWarnings("unchecked")
    private void saveRecipe(ActionEvent event) { 
        //this try/catch nesting goes all the way to recipeModel
        try{
            recipeViewModel.saveRecipe();             
        }catch(CustomValidSaveException e){
            System.out.println("Made it back to CreateRecipeView\n"+e.getMessage());
            ((ObserverModel)this.dataStoreUpdater).setUpdate(CurrentUpdate.RECIPE);
        }        
    }

    private void newRecipe(ActionEvent event){
        recipeViewModel.resetRecipeAll();
    }   

    public void update(CurrentUpdate update) {
        //For now CreateRecipeView isnt looking for any updates. so it doesnt matter what the current update is.
    }

    // adding instruction when loading a recipe
    public void addInstruction(String instruction){
        HBox hboxInstructionViewHolder = new HBox();
        Button btnDeleteInstruction = new Button("x");
        btnDeleteInstruction.setOnAction(e -> removeInstruction(hboxInstructionViewHolder));
        hboxInstructionViewHolder.getChildren().addAll(new Label(instruction), btnDeleteInstruction);
        this.vboxInstructionsList.getChildren().add(hboxInstructionViewHolder);
    }

    // adding instruction from user input
    public void addInstruction(ActionEvent event){
        HBox hboxInstructionViewHolder = new HBox();
        Button btnDeleteInstruction = new Button("x");
        btnDeleteInstruction.setOnAction(e -> removeInstruction(hboxInstructionViewHolder));
        hboxInstructionViewHolder.getChildren().addAll(new Label(this.tfInstruction.getText()), btnDeleteInstruction);
        this.vboxInstructionsList.getChildren().add(hboxInstructionViewHolder);
    }

    //the incoming Hbox may be changed to a InstructionView depending on the needs of the instructions
    public void removeInstruction(HBox instructionView){       
        this.vboxInstructionsList.getChildren().remove(instructionView);
    }

    // test copy, for adding ingredients to a blank recipe from a saved recipe
    public void addIngredient(IngredientView ingredientView){
        System.out.println("addIngredientView.getIngredientName(IngredientView ingredientView), ingredient name: " + this.addIngredientView.getIngredientName()+"\n");
        // IngredientView ingredientView = new IngredientView(this.addIngredientView.getIngredientName(),"1", this.addIngredientView.getIngredientVolume(),this.addIngredientView.getIngredientUnitsOfVolume(), "1", "ounce");
        HBox hboxIngredientViewHolder = new HBox();
        Button btnDeleteIngredient = new Button("x");
        btnDeleteIngredient.setOnAction(e -> removeIngredient(hboxIngredientViewHolder));
        hboxIngredientViewHolder.getChildren().addAll(ingredientView, btnDeleteIngredient);
        this.vboxIngredientsList.getChildren().add(hboxIngredientViewHolder);        
        // IngredientViewHolder.getChildren().addAll(this.vboxIngredientsList, btnDeleteIngredient);
        System.out.println("ObservableList<Node> recipeIngredientNodes size: " + recipeViewModel.recipeIngredientsNodesProperty().size()+"\n\n");
    }

    //add ingredient from user input test copy
    public void addIngredient(ActionEvent event){       
        System.out.println("addIngredientView.getIngredientName(ActionEvent event), ingredient name: " + this.addIngredientView.getIngredientName()+"\n");
        IngredientView ingredientView = new IngredientView(this.addIngredientView.getIngredientName(),"1", this.addIngredientView.getIngredientVolume(),this.addIngredientView.getIngredientUnitsOfVolume(), "1", "ounce");
        HBox hboxIngredientViewHolder = new HBox();
        Button btnDeleteIngredient = new Button("x");
        btnDeleteIngredient.setOnAction(e -> removeIngredient(hboxIngredientViewHolder));
        hboxIngredientViewHolder.getChildren().addAll(ingredientView, btnDeleteIngredient);
        this.vboxIngredientsList.getChildren().add(hboxIngredientViewHolder);        
        // IngredientViewHolder.getChildren().addAll(this.vboxIngredientsList, btnDeleteIngredient);
        System.out.println("ObservableList<Node> recipeIngredientNodes size: " + recipeViewModel.recipeIngredientsNodesProperty().size()+"\n\n");
    }

    public void removeIngredient(HBox ingredientViewHolder){
        System.out.print("removeIngredient(IngredientView ingredientView) fired " + ingredientViewHolder + "\n");
        this.vboxIngredientsList.getChildren().remove(ingredientViewHolder);
    }
    
}
