package com.recipez.views.view_models;

import java.util.ArrayList;

// import javafx.beans.property.BooleanProperty;
// import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;

import com.recipez.models.RecipeModel;
import com.recipez.models.POJO.Ingredient;
import com.recipez.models.POJO.Recipe;
import com.recipez.util.CustomValidSaveException;
import com.recipez.util.ViewToModelConverter;
import com.recipez.views.IngredientView;

// This can be used by both RecipeView and CreateRecipeView... I think... formulate plan for use across both...
public class RecipeViewModel {   
    
    //Data elements
    private final StringProperty recipeName = new SimpleStringProperty();
    // private final ObservableList<Ingredient> recipeIngredients = FXCollections.observableArrayList();
    private final ObservableList<Node> recipeIngredientsNodes = FXCollections.observableArrayList();
    // private final ObservableList<String> recipeInstructions = FXCollections.observableArrayList();
    private final ObservableList<Node> recipeInstructionsNodes = FXCollections.observableArrayList();
    // private final BooleanProperty recipeFilePresent = new SimpleBooleanProperty();

    //Uses data from this viewModel (ReceipeViewModel) to create a new Recipe class.
    private final ViewToModelConverter converter = new ViewToModelConverter();
    
    //I think data persistence for creating and saving a recipe will be here. 
    private final RecipeModel recipeModel = new RecipeModel();

    public StringProperty recipeNameProperty(){
        return this.recipeName;
    }    

    public ObservableList<Node> recipeIngredientsNodesProperty(){
        return this.recipeIngredientsNodes;    
    }

    public ObservableList<Node> recipeInstructionsNodesProperty(){
        return this.recipeInstructionsNodes;    
    }
    
    public String getRecipeName(){
        return recipeName.get();
    }   
    
    public void setRecipeName(String name){
        System.out.print("RecipeViewModel.setRecipeName(): " + name+ "\n");
        if(name == null || name.trim().length() == 0){
            this.recipeName.set("");
        }else{
            this.recipeName.set(name);
        }
        
    }
    
    public ArrayList<String> getInstructions(){
        ArrayList<String> temp = new ArrayList<String>();
        for(Node node : this.recipeInstructionsNodes) {
            String instruction = ((Label)node).getText();
            temp.add(instruction); 
        }
        return temp;
    }

    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> temp = new ArrayList<Ingredient>();
        
        for(Node node : this.recipeIngredientsNodes) {
            System.out.println("\nIn RecipeViewModel.getIngredients() " + ((IngredientView)node).getIngredientName() + "\n");
            Ingredient ingredient = new Ingredient(((IngredientView)node).getIngredientName(), ((IngredientView)node).getIngredientVolume(), ((IngredientView)node).getIngredientUnitsOfVolume());
            temp.add(ingredient);
        }

        return temp;
    }
   
    public void saveTemporaryRecipe(){
        Recipe recipe = converter.toRecipe(this);        
        System.out.println("RecipeViewModel.saveTemporaryrecipe(): "+ recipe);
        try{
            recipeModel.saveTemporaryRecipe(recipe);
        }catch(Exception e){
            System.out.println("Something went wrong when trying to save temporary recipe\n\n");
            e.printStackTrace(); 
        }
        
    }
    @SuppressWarnings("unchecked")
    public void saveRecipe(){
        Recipe recipe = converter.toRecipe(this);
        try{
            String message = "";
            
            // These checks may be added into the Recipe POJO. boolean returns.
            if(recipe.getRecipeName() == ""){
                message += "Missing recipe name.\n";                
            }
            if(recipe.getIngredients().size() == 0) {               
                message += "No ingredients.\n";
            }
            if (recipe.getInstructions().size() == 0){
                message += "No instructions.\n\n";
            }
            // A little hacky, but if anything is populated into the message (which means elemnts of recipe are missing) 
            // it throws an exception with the message created above. It doesnt stop checking after the first missing element
            // because I want the user to know everything thats is still needed.
            if(message.length() != 0){                
                throw new Exception(message);
            }
            recipeModel.saveRecipe(recipe);                      
        }catch(CustomValidSaveException e){
            throw new CustomValidSaveException(e.getMessage());
        }catch(Exception e){            
            System.err.print("FILE NOT SAVED:\n\n" + e.getMessage());
        }        
    }

    //This may be broken with having temp recipe and recipe 
    public Recipe loadTemporaryRecipe(){
        Recipe recipe = recipeModel.loadTemporaryRecipe();  
        return recipe;
    }

    public void resetRecipeAll(){
        this.resetRecipeName();
        this.resetRecipeIngredients();
        this.resetRecipeInstructions();
    }

    public void resetRecipeName(){
        this.recipeName.set("");
    }

    public void resetRecipeIngredients(){
        this.recipeIngredientsNodes.clear();
    }
    
    public void resetRecipeInstructions(){
        this.recipeInstructionsNodes.clear();
    }
}
