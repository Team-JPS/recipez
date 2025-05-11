package com.recipez.views;

import com.recipez.views.view_models.IngredientViewModel;
import com.recipez.views.view_models.RecipeViewModel;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;

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
public class CreateRecipeView extends GridPane implements Observer {

    // Add/Edit Recipe Name UI elements
    private Label lblRecipeName; 
    private ImageView imgRecipeNameSave;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName, btnSaveRecipe, btnNewRecipe;
    private HBox hboxRecipeNameInput, hboxRecipeNameLabel;
    private VBox vboxInputContainer, vboxLabelContainer;
    // private boolean recipeNameToggle;    
    
    private String view; 

    //Header and navigation between ingredients and instructions
    private Label lblRecipeIngredients, lblRecipeInstructions;
    private HBox hboxIngredientsToggleBox, hboxInstructionsToggleBox;
    // Add/remove recipe ingredients UI Elements
    private VBox vboxIngredientsListView, vboxIngredientsList;
    private HBox hboxAddIngredientChoices;
    private ScrollPane spaneIngredientsListHolder;
    private Button btnAddIngredient;
    // private TextField tfIngredientName;
    // private ChoiceBox<String> cboxVolume, cboxUnitsOfVolume, cboxUnitsOfWeight, cboxWeight;


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
    private Subject observerUpdater;
   
  
    // @SuppressWarnings({ "unchecked", "rawtypes" })
    public CreateRecipeView(Subject updater){
        // this.setVgap(5);
        // this.setHgap(5);  
        ColumnConstraints columns = new ColumnConstraints();
        // columns.setFillWidth(true);
        // columns.setHgrow(Priority.ALWAYS); 
        columns.setPercentWidth(50);
        this.getColumnConstraints().addAll(columns);  

        //this.view is a toggle so the screen isnt constantly reseting as it widens and shrinks with the this.layouyBoundsProperty(). 
        //It probably doesnt really matter as the app will eventually size to the device it is being viewed on.
        this.view = "landscape";
        // register this class with with the dataStoreUpdater. Allows changing 
        // of CurrentUpdate, an enum, that the other Views also observe. saveRecipe
        // calls setUpdate(CurrentUpdate currentUpdate) if the Recipe saved 
        // successfully. setUpdate() calls notifyObservers() which lets the other
        // Views know a change was made. 
        updater.registerObserver(this);
        this.observerUpdater = updater;    
        // the create...() methods here populate the UI for this this View.    
        createRecipeNameView();
        createIngredientsListView();
        createInstructionsListView();
        saveResetRecipeView();        
        ingredientsInstructionsHeader();
        //loadRecipe() needs to happen after the UI has been created. 
        loadRecipe();
        bindViewModel(); 
        this.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            double width = newBounds.getWidth();
            if (width < 800.0 && view == "landscape"){
                System.out.println("\nSkinny witches\n");
                this.view = "portrait";
                this.hboxIngredientsToggleBox.setOnMouseClicked(e -> this.portraitViewMode("ingredients"));
                this.hboxInstructionsToggleBox.setOnMouseClicked(e -> this.portraitViewMode("instructions"));
                portraitViewMode("ingredients");
            }else if(width > 800.00 && view == "portrait"){
                System.out.println("\nwide witches\n");
                this.view = "landscape";
                this.hboxIngredientsToggleBox.setOnMouseClicked(null);
                this.hboxInstructionsToggleBox.setOnMouseClicked(null);
                landscapeViewMode();
            }
        });
        
        // System.out.println("\n"+this.getParent()+"\n");
        // this.prefWidthProperty().bind(this.getParent());
        
      
    }
    private void landscapeViewMode(){
        this.getChildren().removeAll(this.vboxInstructionsListView, this.vboxIngredientsListView);
        this.add(this.vboxIngredientsListView, 0, 2);
        this.add(this.vboxInstructionsListView, 1, 2);
        GridPane.setColumnSpan(this.vboxIngredientsListView, 1);
        GridPane.setColumnSpan(this.vboxInstructionsListView, 1);
    }

    private void portraitViewMode(String bringToFront){
        this.getChildren().removeAll(this.vboxInstructionsListView, this.vboxIngredientsListView);
        if(bringToFront.toLowerCase() == "ingredients"){
            this.add(this.vboxIngredientsListView, 0, 2);
            GridPane.setColumnSpan(this.vboxIngredientsListView, 2);
        }if(bringToFront.toLowerCase() == "instructions"){
            this.add(this.vboxInstructionsListView, 0, 2);
            GridPane.setColumnSpan(this.vboxInstructionsListView, 2);
        }
    }


    private void ingredientsInstructionsHeader(){
        
        this.hboxIngredientsToggleBox = new HBox();
        this.hboxInstructionsToggleBox = new HBox();

        this.hboxIngredientsToggleBox.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);
        this.hboxInstructionsToggleBox.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);

        this.hboxIngredientsToggleBox.setAlignment(Pos.CENTER);
        this.hboxInstructionsToggleBox.setAlignment(Pos.CENTER);    

        this.lblRecipeIngredients = new Label("Ingredients");        
        this.lblRecipeInstructions = new Label("Instructions");
        
        this.lblRecipeIngredients.setAlignment(Pos.CENTER);
        this.lblRecipeInstructions.setAlignment(Pos.CENTER);

        this.hboxIngredientsToggleBox.getChildren().add(this.lblRecipeIngredients);
        this.hboxInstructionsToggleBox.getChildren().add(this.lblRecipeInstructions);

        this.add(this.hboxIngredientsToggleBox, 0, 1);
        this.add(this.hboxInstructionsToggleBox, 1,1);

    }

    //  private void createIngredientsListView(){        
    //     this.vboxIngredientsListView = new VBox();
    //     this.vboxIngredientsList = new VBox();
    //     this.addIngredientView = new AddIngredientView();
    //     this.hboxAddIngredientChoices = new HBox();
    //     this.btnAddIngredient = new Button("+");
    //     this.spaneIngredientsListHolder = new ScrollPane();
        
    //     // HBox hboxIngredientsHolder = new HBox();
    //     // hboxIngredientsHolder.setAlignment(Pos.CENTER);
    //     // hboxIngredientsHolder.setMinWidth(200.00);
    //     // hboxIngredientsHolder.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);
    //     // hboxIngredientsHolder.setFit
        
    //     // HBox.setHgrow(hboxIngredientsHolder, Priority.ALWAYS);

    //     this.spaneIngredientsListHolder.setFitToHeight(true);
    //     this.spaneIngredientsListHolder.setPrefViewportHeight(500);
    //     this.spaneIngredientsListHolder.setFitToWidth(true);
    //     // GridPane.setHgrow(this.spaneIngredientsListHolder, Priority.ALWAYS);
      
    //     this.hboxAddIngredientChoices.setAlignment(Pos.CENTER);        
    //     this.btnAddIngredient.setFont(GlobalValues.MEDIUM_FONT);

    //     this.btnAddIngredient.setOnAction(this::addIngredient);
        
    //     // hboxIngredientsHolder.getChildren().add(this.spaneIngredientsListHolder);
    //     this.spaneIngredientsListHolder.setContent(this.vboxIngredientsList);
    //     this.hboxAddIngredientChoices.getChildren().addAll(this.addIngredientView, this.btnAddIngredient);        
    //     this.vboxIngredientsListView.getChildren().addAll(this.spaneIngredientsListHolder, this.hboxAddIngredientChoices);
    //     // this.vboxIngredientsListView.getChildren().addAll(hboxIngredientsHolder, this.hboxAddIngredientChoices);
    //     this.add(this.vboxIngredientsListView, 0, 2);        
    //     this.vboxIngredientsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE); 
    //     this.vboxIngredientsListView.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);       
    // }


    //Working copy
    private void createIngredientsListView(){        
        this.vboxIngredientsListView = new VBox();
        this.vboxIngredientsList = new VBox();
        this.addIngredientView = new AddIngredientView();
        this.hboxAddIngredientChoices = new HBox();
        this.btnAddIngredient = new Button("+");
        this.spaneIngredientsListHolder = new ScrollPane();
        
        HBox hboxIngredientsHolder = new HBox();
        hboxIngredientsHolder.setAlignment(Pos.CENTER);
        hboxIngredientsHolder.setMinWidth(200.00);
        hboxIngredientsHolder.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);
        // hboxIngredientsHolder.setFit
        
        HBox.setHgrow(hboxIngredientsHolder, Priority.ALWAYS);

        this.spaneIngredientsListHolder.setFitToHeight(true);
        this.spaneIngredientsListHolder.setPrefViewportHeight(500);
        this.spaneIngredientsListHolder.setFitToWidth(true);
      
        this.hboxAddIngredientChoices.setAlignment(Pos.CENTER);        
        this.btnAddIngredient.setFont(GlobalValues.MEDIUM_FONT);

        this.btnAddIngredient.setOnAction(this::addIngredient);
        
        hboxIngredientsHolder.getChildren().add(this.spaneIngredientsListHolder);
        this.spaneIngredientsListHolder.setContent(this.vboxIngredientsList);
        this.hboxAddIngredientChoices.getChildren().addAll(this.addIngredientView, this.btnAddIngredient);        
        // this.vboxIngredientsListView.getChildren().addAll(this.spaneIngredientsListHolder, this.hboxAddIngredientChoices);
        this.vboxIngredientsListView.getChildren().addAll(hboxIngredientsHolder, this.hboxAddIngredientChoices);
        this.add(this.vboxIngredientsListView, 0, 2);        
        this.vboxIngredientsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);        
    }

    private void createInstructionsListView(){
        this.vboxInstructionsList = new VBox();
        this.vboxInstructionsListView = new VBox();
        this.hboxAddInstructionOptions = new HBox();
        this.tfInstruction = new TextField("");
        this.btnAddInstruction = new Button("+");
        this.spaneInstructionsListHolder = new ScrollPane();

        HBox hboxInstructionsHolder = new HBox();
        hboxInstructionsHolder.setAlignment(Pos.CENTER);
        hboxInstructionsHolder.setMinWidth(200.00);
        hboxInstructionsHolder.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);
        // hboxIngredientsHolder.setFit
        
        HBox.setHgrow(hboxInstructionsHolder, Priority.ALWAYS);
        
       
        this.spaneInstructionsListHolder.setFitToHeight(true);
        this.spaneInstructionsListHolder.setPrefViewportHeight(500);
        this.spaneInstructionsListHolder.setPrefViewportWidth(300);
        this.spaneInstructionsListHolder.setFitToWidth(true); 
        this.spaneInstructionsListHolder.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);
        
        this.hboxAddInstructionOptions.setAlignment(Pos.CENTER);

        this.btnAddInstruction.setOnAction(this::addInstruction);  
        // this.btnAddInstruction.setAlignment(Pos.BASELINE_RIGHT);  

        hboxInstructionsHolder.getChildren().add(this.spaneInstructionsListHolder);
        this.spaneInstructionsListHolder.setContent(this.vboxInstructionsList);
        this.hboxAddInstructionOptions.getChildren().addAll(this.tfInstruction, this.btnAddInstruction);
        // this.vboxInstructionsListView.getChildren().addAll(this.spaneInstructionsListHolder, this.hboxAddInstructionOptions);
        this.vboxInstructionsListView.getChildren().addAll(hboxInstructionsHolder, this.hboxAddInstructionOptions);
        this.add(this.vboxInstructionsListView, 1, 2);
        this.vboxInstructionsList.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);     
        // this.vboxInstructionsListView.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);   
    }

    
    public void saveResetRecipeView(){
        // save / new recipe buttons
        HBox saveHolder = new HBox();
        HBox newHolder = new HBox();
        this.btnSaveRecipe = new Button("Save Recipe");
        this.btnNewRecipe = new Button("New Recipe");
        this.btnSaveRecipe.setFont(GlobalValues.MEDIUM_FONT);
        this.btnNewRecipe.setFont(GlobalValues.MEDIUM_FONT);
        // this.btnSaveRecipe.setAlignment(Pos.CENTER);
        // this.btnNewRecipe.setAlignment(Pos.CENTER);
        saveHolder.setAlignment(Pos.CENTER_RIGHT);
        newHolder.setAlignment(Pos.CENTER_LEFT);
        this.btnSaveRecipe.setOnAction(this::saveRecipe);
        this.btnNewRecipe.setOnAction(this::newRecipe);

        saveHolder.getChildren().add(this.btnSaveRecipe);
        newHolder.getChildren().add(this.btnNewRecipe);

        // saveHolder.setStyle(GlobalValues.COLOR_TEST_FORMATTING_THREE);

        this.add(saveHolder, 0, 3);
        this.add(newHolder, 1, 3);
    }

    // WORKING COPY
    // UI change, 
    // [HBox][Label][Some UI element][/HBox] <--swaps--> [HBox][TextField][Button][/HBox]
    // The 'Some UI element' will be the swap with the save button. A flower or something, some cute image that
    // sits next to the recipe name   
    private void createRecipeNameView(){
        this.setStyle(GlobalValues.COLOR_PRIMARY);
        this.imgRecipeNameSave = new ImageView();
        // this.recipeNameToggle = true;
        // this.lblUserMessage = new Label("Click to rename your recipe!");
        this.tfRecipeName = new TextField(" ");
        this.lblRecipeName = new Label("");      
        String workingDir = System.getProperty("user.dir");
        // System.out.println("Current working directory: " + workingDir);
        String filePath = workingDir+"\\src\\main\\resources\\images";  
        this.btnSaveRecipeName = new Button("Save");
        System.out.println("\nImage file path:\n"+filePath+"\n");

        try{
            Image flower = new Image("file:///"+filePath+"\\fire_flower.jpg", 75.0, 75.0, false, false, false);
            this.imgRecipeNameSave.setImage(flower);
        }catch(Exception e){
            e.printStackTrace();
        }

        this.lblRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.tfRecipeName.setFont(GlobalValues.LARGE_FONT);
        this.tfRecipeName.setMinWidth(500.00);
        this.lblRecipeName.setMinWidth(500.00);
        this.btnSaveRecipeName.setFont(GlobalValues.MEDIUM_FONT);     

        this.lblRecipeName.setStyle("-fx-padding: 0 20 0 0;"+GlobalValues.COLOR_TEST_FORMATTING_THREE);
        
        this.tfRecipeName.setAlignment(Pos.CENTER);
        this.lblRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipeName.setAlignment(Pos.CENTER);
        
        this.lblRecipeName.setOnMouseClicked(e -> this.swapLayer(false));        
        this.imgRecipeNameSave.setOnMouseClicked(e -> this.swapLayer(false));

        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::saveRecipeName);        
        
        this.hboxRecipeNameInput = new HBox(); 
        this.hboxRecipeNameLabel = new HBox(); 
       
        /*********************************User input screen*********************************/           
        //Hbox container for textfield for user input   
        this.hboxRecipeNameInput.setSpacing(10);
        this.hboxRecipeNameInput.setAlignment(Pos.CENTER);
        this.hboxRecipeNameInput.setStyle(GlobalValues.COLOR_TEST_FORMATTING_ONE);        
        this.hboxRecipeNameInput.getChildren().addAll(this.tfRecipeName, this.btnSaveRecipeName);        
        this.hboxRecipeNameInput.setPrefWidth(GlobalValues.APP_WIDTH);
        this.hboxRecipeNameInput.prefWidthProperty().bind(this.widthProperty());

        /*********************************Input display screen*********************************/
        //Hbox container that displays user input via label             
        this.hboxRecipeNameLabel.setAlignment(Pos.CENTER);
        this.hboxRecipeNameLabel.setStyle(GlobalValues.COLOR_PRIMARY);
        this.hboxRecipeNameLabel.getChildren().addAll(this.lblRecipeName, this.imgRecipeNameSave);        
        this.hboxRecipeNameLabel.setPrefWidth(GlobalValues.APP_WIDTH);
        this.hboxRecipeNameLabel.prefWidthProperty().bind(this.widthProperty());
       
        this.add(this.hboxRecipeNameLabel, 0, 0);
        this.add(this.hboxRecipeNameInput, 0, 0);      

        GridPane.setColumnSpan(hboxRecipeNameInput, 2);
        GridPane.setColumnSpan(hboxRecipeNameLabel, 2);

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

    private void swapLayer(Boolean recipeNameToggle) {
        if(recipeNameToggle){
            Utility.fadeOut(this.vboxInputContainer);
            this.btnSaveRecipeName.setDisable(true);
            this.tfRecipeName.setDisable(true);            
            binarySwap(this.hboxRecipeNameLabel, this.hboxRecipeNameInput);
            Utility.fadeIn(this.hboxRecipeNameLabel);          
        }else{
            Utility.fadeOut(this.vboxLabelContainer);
            this.btnSaveRecipeName.setDisable(false);
            this.tfRecipeName.setDisable(false);            
            binarySwap(this.hboxRecipeNameInput, this.hboxRecipeNameLabel);
            Utility.fadeIn(this.hboxRecipeNameInput); 
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
            ((ObserverModel)this.observerUpdater).setUpdate(CurrentUpdate.RECIPE);
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
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        btnDeleteInstruction.setOnAction(e -> removeInstruction(hboxInstructionViewHolder));
        hboxInstructionViewHolder.getChildren().addAll(new Label(instruction), spacer, btnDeleteInstruction);
        this.vboxInstructionsList.getChildren().add(hboxInstructionViewHolder);
        // btnDeleteInstruction.setAlignment(Pos.BASELINE_RIGHT);
    }

    // adding instruction from user input
    public void addInstruction(ActionEvent event){
        HBox hboxInstructionViewHolder = new HBox();
        Button btnDeleteInstruction = new Button("x");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        btnDeleteInstruction.setOnAction(e -> removeInstruction(hboxInstructionViewHolder));
        hboxInstructionViewHolder.getChildren().addAll(new Label(this.tfInstruction.getText()), spacer, btnDeleteInstruction);
        this.vboxInstructionsList.getChildren().add(hboxInstructionViewHolder);
        // btnDeleteInstruction.setAlignment(Pos.BASELINE_RIGHT);
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
