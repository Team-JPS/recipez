package models.POJO;

import java.util.ArrayList;

public class Recipe {
    // TODO: JavaDoc this class
 
    private String recipeName; 
    private ArrayList<Ingredient> recipeIngredients; 
          
    public Recipe(String name){        
        this.recipeName = name;
        String[] storage = {"Carrot, cheese, Bacon"};
        ArrayList<Ingredient> loadedFromStorage = new ArrayList<Ingredient>();       
        for (String ingredient : storage) {
            loadedFromStorage.add(new Ingredient(ingredient));
        }
        this.recipeIngredients = loadedFromStorage;


    }

    public Recipe(String name, ArrayList<Ingredient> recipeIngredients){
        
    }


    public String getRecipeName(){
        return this.recipeName;
    }

    public void setRecipeName(String name){
        this.recipeName = name;
    }

    public String toString(){
        return "Recipe Information:\nName: "+this.recipeName;
    }

}
