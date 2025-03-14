package com.recipez.models.POJO;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    // TODO: JavaDoc this class
 
    private String recipeName; 
    private ArrayList<Ingredient> recipeIngredients; 
    private ArrayList<String> recipeInstructions;
          
    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<String> instructions){        
        this.recipeName = name;
        this.recipeIngredients = ingredients;
        this.recipeInstructions = instructions;
    }

    // public Recipe(String name){
    //     this.recipeName = name;
    //     this.recipeIngredients = new ArrayList<Ingredient>();
    //     this.recipeInstructions = new ArrayList<String>();
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
        String message = "";
        int count = 1;
        message += "Recipe Information:\nName: "+this.recipeName+"\n";
        message += "Ingredients:\n";
        for(Ingredient ingredient : getIngredients()){
            message += " - " + ingredient.getName() + "\n";
        }
        message += "Instructions:\n";
        for(String instruction : getInstructions()){
            message += " " + count + " " + instruction + "\n";
            count++;
        }
        return message; 
    }

}
