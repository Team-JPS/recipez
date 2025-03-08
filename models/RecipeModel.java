package models;

import models.POJO.Recipe;

//Data save logic is going to be here, but persistence between Views.... Handled in ViewModel files??
public class RecipeModel {
    /**
     * @method save(Recipe recipe) currently uses system out to print a message to show the program made it this far
     * Save to JSON object? 
     *
     *  
     */

    public void save(Recipe recipe){
        System.out.println("Saving: \n" + recipe.toString());
    }
}
