package gui;
import base.Recipe;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class HomeScene extends Scene {

    private Group root;
    private Button addNewRecipeButton, recipeBookButton;  
    
    public HomeScene() {
        super(new Group(), 500, 300, Color.BLACK);
        root = ((Group)this.getRoot());
        addNewRecipeButton = new Button("Add New Recipe");
        root.getChildren().add(addNewRecipeButton);
    }
}
