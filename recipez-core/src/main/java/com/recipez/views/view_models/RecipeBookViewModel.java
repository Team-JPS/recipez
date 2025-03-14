package com.recipez.views.view_models;

import java.util.ArrayList;

import com.recipez.models.RecipeBookModel;
import com.recipez.models.POJO.Ingredient;
import com.recipez.models.POJO.Recipe;

public class RecipeBookViewModel {

    //This is the property that will house the recipe book, I think this will work
    private final ArrayList<Recipe> recipeBook;
    
    public final RecipeBookModel recipeBookModel = new RecipeBookModel();
    //Constructor for this class
    public RecipeBookViewModel(){
        //This will create an ArrayList and put it into the ObservableList<Recipe> recipeBook.
        String[] storage = {"Cheese Burger", "Fries", "Shake", "Salad", "Pizza", "Burrito"};
        this.recipeBook = new ArrayList<>();

        for (String name : storage) {
            recipeBook.add(new Recipe(name, new ArrayList<Ingredient>(), new ArrayList<String>()));
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

    public void loadRecipes() {

    }

    public void loadRecipe() {
        this.recipeBook.add(recipeBookModel.loadRecipe());
    }

}
