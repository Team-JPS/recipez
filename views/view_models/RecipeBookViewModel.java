package views.view_models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.POJO.Recipe;

public class RecipeBookViewModel {

    //This is the property that will house the recipe book, I think this will work
    private final ObservableList<Recipe> recipeBook;

    //Constructor for this class
    public RecipeBookViewModel(){
        //This will create an ArrayList and put it into the ObservableList<Recipe> recipeBook.
        String[] storage = {"Cheese Burger, Fries, Shake"};
        ArrayList<Recipe> loadedFromStorage = new ArrayList<Recipe>();       
        for (String name : storage) {
            loadedFromStorage.add(new Recipe(name));
        }
        //DO NOT GET HUNG UP on the above logic, that is NOT how we are loading our data into the app.

        //loadedFromStorage is an Arraylist<Recipe>
        this.recipeBook = FXCollections.observableArrayList(loadedFromStorage);
    }

    public ObservableList<Recipe> getRecipeBook(){   
        return this.recipeBook;
    }

    public Recipe getRecipe(int index){
        //EXAMPLE ONLY, I would look into ways of retrieving data from an ObservableList
        //I believe this would work just fine. but 
        return this.recipeBook.get(index);
    }
        
}
