package views;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomeView extends Scene {
    private Pane root;      
    private Button btnRecipeCreate, btnRecipeBook, btnMealPlanner;
    private HBox hboxNavigation;
    private VBox vboxApplication, vboxHomeScreen;
    private Label welcome;
    private StackPane spScreen;
    //The views for our application (Create recipe, Recipe Book, Create Meal Plan)
    private RecipeView recipeView;
    
    private final Font smallFont = new Font("Arial", 12);
    private final Font mediumFont = new Font("Arial", 24);
    private final Font largeFont = new Font("Arial", 36);
    
    public HomeView() {
        //Constructor call to parent Scene
        //900x400 is NOT the defining aspect ratio, purely cosmetic to know the work space while coding.
        super(new Pane(), 900, 400);                 
        createView();
    }

    private void createView(){
        //intializing 
        root = ((Pane)this.getRoot());
        this.recipeView = new RecipeView();
        // this.recipeBookView = new RecipeBookView();
        
        //This StackPane will be where our views will display
        this.spScreen = new StackPane();

        this.btnRecipeCreate = new Button("New Recipe");
        this.btnRecipeBook = new Button("Recipe Book");
        this.btnMealPlanner = new Button("Meal Planner");
        this.welcome = new Label("Welcome!");
      
        this.hboxNavigation = new HBox();
        this.vboxApplication = new VBox();
        this.vboxHomeScreen = new VBox();

        //setting up the fluff, the pretty stuff
        this.welcome.setFont(largeFont);

        //Vbox for welcome screen
        this.vboxHomeScreen.setPrefHeight(300);
        this.vboxHomeScreen.setAlignment(Pos.CENTER);
        this.vboxHomeScreen.setStyle("-fx-background-color: #fccdb6");

        this.hboxNavigation.setPrefWidth(900);
        this.hboxNavigation.setAlignment(Pos.CENTER);

        this.vboxHomeScreen.getChildren().addAll(this.welcome);

        this.btnRecipeCreate.setOnAction(this::navigation);
        this.btnRecipeBook.setOnAction(this::navigation);
        this.btnMealPlanner.setOnAction(this::navigation);

        this.spScreen.getChildren().addAll(vboxHomeScreen);
        this.hboxNavigation.getChildren().addAll(btnRecipeCreate, btnRecipeBook, btnMealPlanner);
        this.vboxApplication.getChildren().addAll(spScreen, hboxNavigation);
        this.root.getChildren().addAll(vboxApplication);
    }

    private void navigation(ActionEvent evt){
        String buttonText = ((Button)evt.getSource()).getText();
        if (buttonText == "New Recipe") {
            this.spScreen.getChildren().clear();
            this.spScreen.getChildren().addAll(this.recipeView);
            ((Button)evt.getSource()).setDisable(true); 
            // need to add focus back to the tfRecipeName in the RecipeView... ??
        }
        // if (buttonText == "Recipe Book") {
        //     this.spScreen.getChildren().clear();
        //     this.spScreen.getChildren().addAll(this.recipeBookView);
        //     ((Button)evt.getSource()).setDisable(true); 
        //     // need to add focus back to the tfRecipeName in the RecipeView... ??
        // }

    }
}
