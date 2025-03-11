package models.POJO;

import java.util.ArrayList;

public class RecipeBook {
    
    private ArrayList<Recipe> recipes;

    public RecipeBook() {
        this.recipes = new ArrayList<Recipe>();
    }

    public ArrayList<Recipe> getRecipes() {
        return this.recipes;
    }
    
}


