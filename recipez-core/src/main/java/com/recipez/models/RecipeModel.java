package com.recipez.models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.recipez.models.POJO.Recipe;

//Data save logic is going to be here, but persistence between Views.... Handled in ViewModel files??
public class RecipeModel {
    /**
     * @method save(Recipe recipe) currently uses system out to print a message to show the program made it this far
     * Save to JSON object? 
     *
     *  
     */

    public void save(Recipe recipe){
        System.out.println("Saving recipe: \n" + recipe.toString());        
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + workingDir);
        String filePath = workingDir+"\\src\\main\\resources\\data";        
        // Create a Gson instance
        Gson gson = new Gson();
        // Convert the Java object to JSON and write it to a file
        try (FileWriter writer = new FileWriter(filePath+"\\recipe.json")) {
            gson.toJson(recipe, writer);
            System.out.println("Java object successfully written to recipe.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Recipe load() {
        String workingDir = System.getProperty("user.dir");
        String filePath = workingDir+"\\src\\main\\resources\\data";
        Recipe recipe;        
        // Read Json from a file
        // Create a Gson instance
        Gson gson = new Gson();
        File check = new File(filePath+"\\recipe.json");
        
        
        if(check.exists()){
            System.out.print("RECIPE IS PRESENT");
            try {
                Reader reader = new FileReader(filePath+"\\recipe.json");
                // convert the JSON data to a Java object
                recipe = gson.fromJson(reader, Recipe.class);
                System.out.print("CHUCKY CHEESES");
                System.out.println(recipe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //we should probably return an empty Recipe instead of null
            recipe = new Recipe("temp");
        }
        return recipe;
    }
}
