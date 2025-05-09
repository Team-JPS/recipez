package com.recipez.models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.recipez.models.POJO.Ingredient;
import com.recipez.models.POJO.Recipe;

//Data save logic is going to be here, but persistence between Views.... Handled in ViewModel files??
public class RecipeBookModel {
    /**
     * @method save(Recipe recipe) saves a Recipe class to a JSON file. 
     */



    // public void save(Recipe recipe){
    //     System.out.println("Saving recipe: \n" + recipe.toString());        
    //     String workingDir = System.getProperty("user.dir");
    //     // System.out.println("Current working directory: " + workingDir);
    //     String filePath = workingDir+"\\src\\main\\resources\\data";        
    //     // Create a Gson instance
    //     Gson gson = new Gson();
    //     File recipeTempFile = new File(filePath+"\\recipeTemp.json");
    //     // Convert the Java object to JSON and write it to a file
    //     try (FileWriter writer = new FileWriter(filePath+"\\recipe.json")) {
    //         gson.toJson(recipe, writer);
    //         if(recipeTempFile.exists()){
    //             recipeTempFile.delete();
    //         }
    //         System.out.println("Java object successfully written to recipe.json");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public void saveTemporaryRecipe(Recipe recipe){
    //     System.out.println("Saving recipe: \n" + recipe.toString());        
    //     String workingDir = System.getProperty("user.dir");
    //     // System.out.println("Current working directory: " + workingDir);
    //     String filePath = workingDir+"\\src\\main\\resources\\data";        
    //     // Create a Gson instance
    //     Gson gson = new Gson();
    //     // Convert the Java object to JSON and write it to a file
    //     try (FileWriter writer = new FileWriter(filePath+"\\recipeTemp.json")) {
    //         gson.toJson(recipe, writer);
    //         System.out.println("Java object successfully written to recipeTemp.json");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }


    // public Recipe loadTemporaryRecipe() {
    //     //The logic in here is rough, needs rework
    //     String workingDir = System.getProperty("user.dir");
    //     String filePath = workingDir+"\\src\\main\\resources\\data";
    //     Recipe recipe;        
        
    //     Gson gson = new Gson();
        
    //     // looks for a file and assigns what it finds to check
    //     File check = new File(filePath+"\\recipeTemp.json");
        
    //     //if the file exists
    //     if(check.exists()){
    //         System.out.print("TEMPORARY RECIPE IS PRESENT\n");
    //         try {
    //             Reader reader = new FileReader(filePath+"\\recipeTemp.json");
    //             // convert the JSON data to a Java object
    //             recipe = gson.fromJson(reader, Recipe.class);
    //             System.out.println(recipe);
    //         } catch (IOException e) {
    //             throw new RuntimeException(e);
    //         }
    //     }else{
    //         System.out.print("NO TEMPORARY RECIPE FILE PRESENT, LOADING EMPTY RECIPE\n");
    //         recipe = new Recipe("");
    //     }
    //     return recipe;
    // }

    public Recipe loadRecipe() {
        //The logic in here is rough, needs rework
        String workingDir = System.getProperty("user.dir");
        String filePath = workingDir+"\\src\\main\\resources\\data";
        Recipe recipe;        
        
        Gson gson = new Gson();
        
        // looks for a file and assigns what it finds to check
        File check = new File(filePath+"\\recipe.json");
        
        //if the file exists
        if(check.exists()){
            System.out.print("RECIPE IS PRESENT\n");
            try {
                Reader reader = new FileReader(filePath+"\\recipe.json");
                // convert the JSON data to a Java object
                recipe = gson.fromJson(reader, Recipe.class);
                System.out.println(recipe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.print("NO RECIPE FILE PRESENT, LOADING EMPTY RECIPE");
            recipe = new Recipe("", new ArrayList<Ingredient>(), new ArrayList<String>());
        }
        return recipe;
    }
}

