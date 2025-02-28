package util;

import models.POJO.Recipe;
import views.view_models.RecipeViewModel;

public class RecipeConverter {
    
    public Recipe toRecipe(RecipeViewModel viewModel){
        return new Recipe(viewModel.getName());
    }
}
