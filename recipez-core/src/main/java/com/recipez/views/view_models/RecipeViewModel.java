package com.recipez.views.view_models;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.recipez.models.RecipeModel;
import com.recipez.models.POJO.Ingredient;
import com.recipez.models.POJO.Recipe;
import com.recipez.util.ViewToModelConverter;

// This can be used by both RecipeView and CreateRecipeView... I think... formulate plan for use across both...
public class RecipeViewModel {   
    
    //Data elements
    private final StringProperty recipeName = new SimpleStringProperty();
    private final ObservableList<Ingredient> recipeIngredients = FXCollections.observableArrayList();
    private final ObservableList<String> recipeInstructions = FXCollections.observableArrayList();
    
    //Uses data from this viewModel (ReceipeViewModel) to create a new Recipe class.
    private final ViewToModelConverter converter = new ViewToModelConverter();
    
    //I think data persistence for creating and saving a recipe will be here. 
    private final RecipeModel recipeModel = new RecipeModel();

    public ObservableList<String> instructionsProperty(){
        return this.recipeInstructions;
    }

    public ArrayList<String> getInstructions(){
        ArrayList<String> temp = new ArrayList<String>();
        for(String instruction : this.recipeInstructions.stream().toList() ){
            temp.add(instruction);
        }
        return temp;
    }

    public void setInstructions(ArrayList<String> instructions){
        this.recipeInstructions.setAll(instructions);
    }

    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> temp = new ArrayList<Ingredient>();
        for(Ingredient ingredient: this.recipeIngredients.stream().toList()){
            temp.add(ingredient);
        }
        return temp;
    }

    public ObservableList<Ingredient> ingredientsProperty(){
        return this.recipeIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.recipeIngredients.setAll(ingredients);
    }

    public void addIngredient(Ingredient newIngredient){
        this.recipeIngredients.add(newIngredient);
    }

    public void removeIngredient(Ingredient ingredientToRemove){
        this.recipeIngredients.remove(ingredientToRemove);
    }

    public String getName(){
        return recipeName.get();
    }

    public StringProperty nameProperty(){
        return recipeName;
    } 
    
    public void setName(String name){
        System.out.println("setName() in the RecipeViewModel class is being called\nName: " + name);
        if(name == null || name.trim() == ""){
            System.out.print("Setting Name to Suspicious Nachos\n");
            this.recipeName.set("Suspicious Nachos");            
        }else{
            this.recipeName.set(name);
        }        
    }

    public void save(){
        //this may need to have a return of a message about missing data or instead of a return have a try/catch that the save is called inside of to catch
        //missing name, ingredients, or instructions. 
        Recipe recipe = converter.toRecipe(this);
        recipeModel.save(recipe);
    }

    public void load(){
        recipeModel.loadRecipe();
    }


    public void reset(){
        this.recipeName.set("");
    }

    public StringProperty recipeNameProperty() { return recipeName;}
}
