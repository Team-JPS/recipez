package models;
import java.util.ArrayList;

public class Recipe {
    // TODO: JavaDoc this class
    
    private String recipeName;    //
    private ArrayList<FoodItem> ingredients;
    private ArrayList<String> instructions;
    
    public Recipe(){
        this.recipeName = "";
        this.ingredients = new ArrayList<FoodItem>();
        this.instructions = new ArrayList<String>();
    }

    // Add Setter and Getters 
}
