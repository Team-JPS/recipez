package models.POJO;

import java.util.ArrayList;

public class Recipe {
    // TODO: JavaDoc this class
 
    private String recipeName; 
    private ArrayList<Ingredient> recipeIngredients; 
    private ArrayList<String> recipeInstructions;
          
    public Recipe(String name){        
        this.recipeName = name;
        this.recipeIngredients = new ArrayList<Ingredient>();
        this.recipeInstructions = new ArrayList<String>();
    }

    // public Recipe(String name, ArrayList<Ingredient> recipeIngredients){
        
    // }

    public void setInstructions(ArrayList<String> instructions){
        this.recipeInstructions = instructions;
    }

    public ArrayList<String> getInstructions(){
        return this.recipeInstructions;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.recipeIngredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients(){
        return this.recipeIngredients;
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
