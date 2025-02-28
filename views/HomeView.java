package views;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class HomeView extends Scene {
    private Group root;      
    public HomeView() {
        super(new Group(), 900, 300, Color.BLACK);
        root = ((Group)this.getRoot());        
        RecipeView recipeView = new RecipeView();
        root.getChildren().add(recipeView);
    }
}
