package com.recipez.views;

import java.io.File;
import java.nio.file.FileSystems;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.recipez.models.RecipeDataStoreModel;
import com.recipez.util.GlobalValues;

public class HomeView extends Scene {
    private Pane root;      
    private Button btnCreateRecipe, btnRecipeBook, btnMealPlanner;
    private HBox hboxNavigation;
    private VBox vboxApplication, vboxHomeScreen;
    private Label lblWelcome;
    private StackPane spViewDisplay;

    //The views for our application 
    private CreateRecipeView createRecipeView;
    private RecipeBookView recipeBookView;
    // private MealPlannerView MealPlannerView;

    //Observer Pattern solution (INCOMPLETE)
    private RecipeDataStoreModel recipeDataStoreModel = new RecipeDataStoreModel();
    
    public HomeView() {
        //Constructor call super() to parent Scene
        //Change varbiable values in GlobalValues class to alter aspect ratio.
       
        // Creates a local directory for data persistence. Recipe,
        // RecipeBook, and MealPlan will be saved here. T 
        super(new Pane(), GlobalValues.APP_WIDTH, GlobalValues.APP_HEIGHT);                 
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + workingDir);
        if(new File(workingDir+"\\src\\main\\resources\\data").mkdir()){
            System.out.println("Directory created:\n"+workingDir+"\\src\\main\\resources\\data");
        }else{
            System.out.println("Directory already exists:\n"+workingDir+"\\src\\main\\resources\\data");
        }
        createView();
    }

    private void createView(){
        //intializing 
        root = ((Pane)this.getRoot());
        this.createRecipeView = new CreateRecipeView();
        this.recipeBookView = new RecipeBookView();
        // this.mealPlannerView = new MealPlannerView();

        //Application Navigation buttons initialized
        this.btnCreateRecipe = new Button("New Recipe");
        this.btnRecipeBook = new Button("Recipe Book");
        this.btnMealPlanner = new Button("Meal Planner");

        // vboxHomeScreen is the element that will be swapped out for our views
        // lblWelcome stores a message to the user when first starting app, populate in vBoxHomeScreen.
        this.vboxHomeScreen = new VBox();
        this.lblWelcome = new Label("Welcome!");      
        
        // vboxApplication is where the the whole of the application will be housed. Vbox was chosen to stack our views with the navigation below it. 
        // The spViewDisplay StackPane is where our views will display
        this.vboxApplication = new VBox();
        this.spViewDisplay = new StackPane();
        this.hboxNavigation = new HBox();
        
        //setting up the fluff, the pretty stuff
        this.lblWelcome.setFont(GlobalValues.LARGE_FONT);

        //Vbox for welcome screen prettified
        this.vboxHomeScreen.setPrefHeight(GlobalValues.VIEW_HEIGHT);
        this.vboxHomeScreen.setAlignment(Pos.CENTER);
        this.vboxHomeScreen.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);

        //navigation holder prettified
        this.hboxNavigation.setPrefWidth(GlobalValues.APP_WIDTH);
        this.hboxNavigation.setPrefHeight(GlobalValues.NAV_HEIGHT);
        this.hboxNavigation.setAlignment(Pos.CENTER);

        //setting up action for click on navigation buttons
        this.btnCreateRecipe.setOnAction(this::navigation);
        this.btnRecipeBook.setOnAction(this::navigation);
        this.btnMealPlanner.setOnAction(this::navigation);

        // Putting the UI elements together
        this.vboxHomeScreen.getChildren().addAll(this.lblWelcome);
        this.spViewDisplay.getChildren().addAll(vboxHomeScreen);
        this.hboxNavigation.getChildren().addAll(btnCreateRecipe, btnRecipeBook, btnMealPlanner);
        this.vboxApplication.getChildren().addAll(spViewDisplay, hboxNavigation);
        this.root.getChildren().addAll(vboxApplication);
    }

    //Fixed navigation issue, buttons will disable/enable as appropriate. May need a efficiency rework
    private void navigation(ActionEvent event){
        String buttonText = ((Button)event.getSource()).getText();
        if (buttonText == "New Recipe") {
            for(Node button : this.hboxNavigation.getChildren()){
                ((Button)button).setDisable(false);
            }
            this.spViewDisplay.getChildren().clear();
            this.spViewDisplay.getChildren().add(this.createRecipeView);
            ((Button)event.getSource()).setDisable(true); 
        }else if (buttonText == "Recipe Book") {
            for(Node button : this.hboxNavigation.getChildren()){
                ((Button)button).setDisable(false);
            }
            this.spViewDisplay.getChildren().clear();
            this.spViewDisplay.getChildren().add(this.recipeBookView);
            ((Button)event.getSource()).setDisable(true); 
        }else if (buttonText == "Meal Planner") {
            for(Node button : this.hboxNavigation.getChildren()){
                ((Button)button).setDisable(false);
            }
            // this.spViewDisplay.getChildren().clear();
            // this.spViewDisplay.getChildren().add(this.mealPlannerView);
            ((Button)event.getSource()).setDisable(true); 
        }
    }
}
