package com.recipez.views.view_models;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private final BooleanProperty recipeFilePresent = new SimpleBooleanProperty();

    //Uses data from this viewModel (ReceipeViewModel) to create a new Recipe class.
    private final ViewToModelConverter converter = new ViewToModelConverter();
    
    //I think data persistence for creating and saving a recipe will be here. 
    private final RecipeModel recipeModel = new RecipeModel();

    public StringProperty recipeNameProperty(){
        return this.recipeName;
    } 

    public ObservableList<Ingredient> recipeIngredientsProperty(){
        return this.recipeIngredients;
    }

    public ObservableList<String> recipeInstructionsProperty(){
        return this.recipeInstructions;
    }    

    public BooleanProperty recipefilePresentProperty(){
        return this.recipeFilePresent;
    }

    public String getName(){
        return recipeName.get();
    }   
    
    public void setName(String name){
        this.recipeName.set(name);
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

    public void removeInstruction(String instruction){
        this.recipeInstructions.remove(instruction);
    }

    public void addInstruction(String instruction){
        this.recipeInstructions.add(instruction);
    }

    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> temp = new ArrayList<Ingredient>();
        for(Ingredient ingredient: this.recipeIngredients.stream().toList()){
            temp.add(ingredient);
        }
        return temp;
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

    public void setRecipeFilePresent(Boolean bool){
        this.recipeFilePresent.set(bool);
    }

    public Boolean getRecipeFilePresent(){
        return this.recipeFilePresent.getValue();
    }

    public void save(){
        Recipe recipe = converter.toRecipe(this);
        try{
            String message = "";
            if(recipe.getRecipeName() == ""){
                message += "Missing recipe name.\n";                
            }
            if(recipe.getIngredients().size() == 0) {               
                message += "No ingredients.\n";
            }
            if (recipe.getInstructions().size() == 0){
                message += "No instructions.\n\n";
            }
            System.out.print("\n\nSAVE ERROR MESSAGE:\n" + message);
            if(message.length() != 0){
                throw new Exception(message);
            }
            recipeModel.save(recipe);            
        }catch(Exception e){            
            System.err.print("FILE NOT SAVED:\n\n" + e.getMessage());
        }        
    }

    public void loadRecipe(){
        Recipe recipe = recipeModel.load();
        if(recipe.getRecipeName().length() == 0 && recipe.getIngredients().size() == 0 && recipe.getInstructions().size() == 0){
            this.setRecipeFilePresent(false);    
        }else{
            this.setRecipeFilePresent(true);
        }
        this.setName(recipe.getRecipeName());
        this.setIngredients(recipe.getIngredients());
        this.setInstructions(recipe.getInstructions());    
    }

    public void reset(){
        this.recipeName.set("");
        
    }
    
}
