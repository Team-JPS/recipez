package com.recipez.util;

import com.recipez.models.POJO.Recipe;
import com.recipez.views.view_models.RecipeViewModel;

public class ViewToModelConverter {
    
    // Recieves a RecipeViewModel and returns a Recipe
    public Recipe toRecipe(RecipeViewModel recipeViewModel){
        return new Recipe(recipeViewModel.getName(), recipeViewModel.getIngredients(), recipeViewModel.getInstructions());
    }

    
}
