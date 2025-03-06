package views;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.POJO.Recipe;
import util.GlobalValues;
import views.view_models.RecipeBookViewModel;

//This extends StackPane, but you can change it to whatever UI element works best for your View Layout.
public class RecipeBookView extends StackPane {

    //properties
    private Label lblRecipeName;
    private Button nextRecipe, previousRecipe;
    private HBox hboxRecipeBook;
    private VBox vboxRecipeBookContainer;

    //This is the data store where you will save and load data from, for this View.
    //When you call 'new RecipeBookViewModel()', anything that you create in the constructor for the 
    //RecipeBookViewModel class will be available in this 'store'. 
    private final RecipeBookViewModel recipeBookViewModel = new RecipeBookViewModel();

    //Constructor for this class
    public RecipeBookView() {
       //createView() will populate the UI.
       createView();
    }

    public void createView(){
        //intialize the properties and Create the UI for recipe Book here. Any data you need to 
        //display will come from this.recipeBookViewModel
        this.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);
        this.setWidth(GlobalValues.APP_WIDTH);
        this.setHeight(GlobalValues.VIEW_HEIGHT);
        this.setMinHeight(GlobalValues.VIEW_HEIGHT);

    }

    //As of now the recipeBookViewModel only has a method that gives you an ObservableList, recipeBookViewModel.getRecipeBook(),
    //You need to figure a way to get it from a ObservableList to an ArrayList. I am certain there is a way to do it. Google Magic.
    //I would handle it inside RecipeBookViewModel and create a method that returns an ArrayList instead of a ObservableList. 
    //Do not delete the current getRecipeBook(), add another that returns a ArrayList<Recipe>.
    public ArrayList<Recipe> searchRecipes(String typeOfSort) {
        ObservableList<Recipe> book = this.recipeBookViewModel.getRecipeBook();
        //soooo apparantly this is a way to get the ArrayList out of the ObservableList, I would use the logic in this return statement 
        // in the RecipeBookViewModel to return the array list
        return ((ArrayList<Recipe>)book.stream().toList());
    }

    //Same goes for this. 
    public ArrayList<Recipe> sortByName() {
        return new ArrayList<>();
    }
    
}
