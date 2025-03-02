package views;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class HomeView extends Scene {
    private Pane root;      
    public HomeView() {
        //Constructor call to parent Scene
        super(new Pane(), 900, 300);
        //intializing 
        root = ((Pane)this.getRoot());        
        RecipeView recipeView = new RecipeView();        
        root.getChildren().add(recipeView);
    }
}
