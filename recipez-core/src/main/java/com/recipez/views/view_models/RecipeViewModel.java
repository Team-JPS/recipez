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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.recipez.models.RecipeModel;
import com.recipez.models.POJO.Ingredient;
import com.recipez.models.POJO.Recipe;
import com.recipez.util.CustomValidSaveException;
import com.recipez.util.ViewToModelConverter;

// This can be used by both RecipeView and CreateRecipeView... I think... formulate plan for use across both...
public class RecipeViewModel {   
    
    //Data elements
    private final StringProperty recipeName = new SimpleStringProperty();
    // private final ObservableList<Ingredient> recipeIngredients = FXCollections.observableArrayList();
    private final ObservableList<Node> recipeIngredientNodes = FXCollections.observableArrayList();
    // private final ObservableList<String> recipeInstructions = FXCollections.observableArrayList();
    private final ObservableList<Node> recipeInstructionNodes = FXCollections.observableArrayList();
    // private final BooleanProperty recipeFilePresent = new SimpleBooleanProperty();

    //Uses data from this viewModel (ReceipeViewModel) to create a new Recipe class.
    private final ViewToModelConverter converter = new ViewToModelConverter();
    
    //I think data persistence for creating and saving a recipe will be here. 
    private final RecipeModel recipeModel = new RecipeModel();

    public StringProperty recipeNameProperty(){
        return this.recipeName;
    } 

    // public ObservableList<Ingredient> recipeIngredientsProperty(){
    //     return this.recipeIngredients;
    // }

    public ObservableList<Node> recipeIngredientsNodesProperty(){
        return this.recipeIngredientNodes;    
    }

    public ObservableList<Node> recipeInstructionsNodesProperty(){
        return this.recipeInstructionNodes;    
    }

    // public ObservableList<String> recipeInstructionsProperty(){
    //     return this.recipeInstructions;
    // }    

    // public BooleanProperty recipefilePresentProperty(){
    //     return this.recipeFilePresent;
    // }

    // public void setRecipeIngredientNodes(ObservableList<Node> observableList){
    //     //vbox box nodes at the moment
    //     this.recipeIngredientNodes.setAll(observableList);
    // }


    public String getName(){
        return recipeName.get();
    }   
    
    public void setName(String name){
        System.out.print("NAMING COMING INTO setName(): " + name+ "\n");
        if(name == null || name.trim().length() == 0){
            this.recipeName.set("");
        }else{
            this.recipeName.set(name);
        }
        
    }

    // public ArrayList<String> getInstructions(){
    //     ArrayList<String> temp = new ArrayList<String>();
    //     for(String instruction : this.recipeInstructions.stream().toList() ){
    //         temp.add(instruction);
    //     }
    //     return temp;
    // }

    // public void setInstructions(ArrayList<String> instructions){
    //     this.recipeInstructions.setAll(instructions);
    // }  

    // public void removeInstruction(String instruction){
    //     this.recipeInstructions.remove(instruction);
    // }

    // public void addInstruction(String instruction){
    //     this.recipeInstructions.add(instruction);
    // }

    public ArrayList<String> getInstructions(){
        ArrayList<String> temp = new ArrayList<String>();
        for(Node node : this.recipeInstructionNodes) {
            // This line broke my brain to write, and the index is dependent on how you add them in the CreateRecipeView.java... be mindful of the order.
            String instruction = ((Label)node).getText();
            temp.add(instruction); 
        }
        // for(Ingredient ingredient: this.recipeIngredients.stream().toList()){
        //     temp.add(ingredient);
        // }
        return temp;
    }


    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> temp = new ArrayList<Ingredient>();
        for(Node node : this.recipeIngredientNodes) {
            // This line broke my brain to write, and the index is dependent on how you add them in the CreateRecipeView.java... be mindful of the order.
            Ingredient ingredient = new Ingredient(((Label)((HBox)node).getChildren().get(0)).getText());
            ingredient.setVolume(((Label)((HBox)node).getChildren().get(1)).getText());
            ingredient.setUnitOfVolume(((Label)((HBox)node).getChildren().get(2)).getText());
            temp.add(ingredient);
        }
        // for(Ingredient ingredient: this.recipeIngredients.stream().toList()){
        //     temp.add(ingredient);
        // }
        return temp;
    }
   
    // public void setIngredients(ArrayList<Ingredient> ingredients){
    //     this.recipeIngredients.setAll(ingredients);
    // }   

    // public void addIngredient(Ingredient newIngredient){
    //     this.recipeIngredients.add(newIngredient);
    // }

    // public void removeIngredient(Ingredient ingredientToRemove){
    //     this.recipeIngredients.remove(ingredientToRemove);
    // }

    // public void setRecipeFilePresent(Boolean bool){
    //     this.recipeFilePresent.set(bool);
    // }

    // public Boolean getRecipeFilePresent(){
    //     return this.recipeFilePresent.getValue();
    // }

    public void saveTemporaryRecipe(){
        Recipe recipe = converter.toRecipe(this);
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
            if(recipe.getRecipeName() == ""){
                message += "Missing recipe name.\n";                
            }
            if(recipe.getIngredients().size() == 0) {               
                message += "No ingredients.\n";
            }
            if (recipe.getInstructions().size() == 0){
                message += "No instructions.\n\n";
            }
            // System.out.print("\n\nSAVE ERROR MESSAGE:\n" + message);
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
        // if(recipe.getRecipeName().length() == 0 && recipe.getIngredients().size() == 0 && recipe.getInstructions().size() == 0){
        //     this.setRecipeFilePresent(false);    
        // }else{
        //     this.setRecipeFilePresent(true);
        // }
        // this.setName(recipe.getRecipeName());
        // this.setIngredients(recipe.getIngredients());
        // this.setInstructions(recipe.getInstructions());    

        return recipe;
    }

    public void resetRecipeAll(){
        this.resetRecipeName();
        // this.resetRecipeIngredients();
        // this.resetRecipeInstructions();
    }

    public void resetRecipeName(){
        this.recipeName.set("");
    }

    // public void resetRecipeIngredients(){
    //     this.recipeIngredients.clear();
    // }
    
    // public void resetRecipeInstructions(){
    //     this.recipeInstructions.clear();
    // }
}
