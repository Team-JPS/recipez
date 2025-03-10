package views.view_models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.POJO.Recipe;

public class RecipeBookViewModel {

    //This is the property that will house the recipe book, I think this will work
    private final ArrayList<Recipe> recipeBook;

    //Constructor for this class
    public RecipeBookViewModel(){
        //This will create an ArrayList and put it into the ObservableList<Recipe> recipeBook.
        String[] storage = {"Cheese Burger", "Fries", "Shake", "Salad", "Pizza", "Burrito"};
        this.recipeBook = new ArrayList<>();

        for (String name : storage) {
            recipeBook.add(new Recipe(name));
        }
        //DO NOT GET HUNG UP on the above logic, that is NOT how we are loading our data into the app.
    }

    public ArrayList<Recipe> getRecipeBook(){   
        return recipeBook;
    }

    //gets a recipe by index
    public Recipe getRecipe(int index) {
        if (index >= 0 && index < recipeBook.size()) {
            return recipeBook.get(index);
        }
        return null;
    }

    
        
}
