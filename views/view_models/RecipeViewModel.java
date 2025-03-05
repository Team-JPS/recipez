package views.view_models;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.RecipeModel;
import models.POJO.Ingredient;
import models.POJO.Recipe;
import util.ViewToModelConverter;


public class RecipeViewModel {   
    
    //Data elements
    private final StringProperty recipeName = new SimpleStringProperty();
    private final ObservableList<Ingredient> recipeIngredients = FXCollections.observableArrayList();
    private final ObservableList<String> recipeInstructions = FXCollections.observableArrayList();
    
    //Uses data from this viewModel to create a new Recipe class.
    private final ViewToModelConverter converter = new ViewToModelConverter();
    
    private final RecipeModel recipeModel = new RecipeModel();

    public ObservableList<String> instructionsProperty(){
        return this.recipeInstructions;
    }

    public ArrayList<String> getInstructions(){
        return (ArrayList<String>)this.recipeInstructions.stream().toList();
    }

    public void setInstructions(ArrayList<String> instructions){
        this.recipeInstructions.setAll(instructions);
    }

    public ArrayList<Ingredient> getIngredients(){
        return (ArrayList<Ingredient>)this.recipeIngredients.stream().toList();
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
        System.out.println("setName() in RecipeViewModel() being called\nName: " + name);
        if(name == null || name.trim() == ""){
            System.out.print("Setting Name to Suspicious Nachos\n");
            this.recipeName.set("Suspicious Nachos");            
        }else{
            this.recipeName.set(name);
        }        
    }

    public void save(){
        Recipe recipe = converter.toRecipe(this);
        recipeModel.save(recipe);
    }

    public void reset(){
        this.recipeName.set("");
    }

    public StringProperty recipeNameProperty() { return recipeName;}
}
