package com.recipez.util;

import com.recipez.models.POJO.Recipe;
import com.recipez.views.view_models.RecipeViewModel;

public class ViewToModelConverter {
    
    // TODO: May need to be updated when 'instructions' and 'ingredients' are a part of the Recipe class
    // Recieves a RecipeViewModel and returns a Recipe
    public Recipe toRecipe(RecipeViewModel viewModel){
        return new Recipe(viewModel.getName(), viewModel.getIngredients(), viewModel.getInstructions());
    }

    
}
