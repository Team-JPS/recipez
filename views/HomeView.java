package views;

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

public class HomeView extends Scene {
    private Pane root;      
    private Button btnRecipeCreate, btnRecipeBook, btnMealPlanner;
    private HBox hboxNavigation;
    private VBox vboxApplication, vboxHomeScreen;
    private Label welcome;
    private StackPane spScreen;
    private RecipeView recipeView;
    
    public HomeView() {
        //Constructor call to parent Scene
        super(new Pane(), 900, 400);                
        createView();
    }

    private void createView(){
        //intializing 
        root = ((Pane)this.getRoot());
        this.recipeView = new RecipeView();

        this.btnRecipeCreate = new Button("New Recipe");
        this.btnRecipeBook = new Button("Recipe Book");
        this.btnMealPlanner = new Button("Meal Planner");

        this.spScreen = new StackPane();
      
        this.hboxNavigation = new HBox();
        this.vboxApplication = new VBox();
        this.vboxHomeScreen = new VBox();

        //Vbox for welcome screen
        this.vboxHomeScreen.setPrefHeight(300);
        this.vboxHomeScreen.setAlignment(Pos.CENTER);
        this.vboxHomeScreen.setStyle("-fx-background-color: #fccdb6");

        this.hboxNavigation.setPrefWidth(900);
        this.hboxNavigation.setAlignment(Pos.CENTER);

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
    }
}
