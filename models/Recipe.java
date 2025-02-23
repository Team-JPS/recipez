package models;
import java.util.ArrayList;


public class Recipe {
    // TODO: JavaDoc this class
 
    private String recipeName;  
    private ArrayList<FoodItem> ingredients;
    private ArrayList<String> instructions;
        
    /**
     * If user doesnt immediately name the recipe, but we need a Recipe object available
     * The name should be a value we can check against?
     **/
    public Recipe(){        
        this("Choose a name.");
    }

    public Recipe(String name){        
        this.recipeName = name;
        this.ingredients = new ArrayList<FoodItem>();
        this.instructions = new ArrayList<String>();
    }

    // Add Setter and Getters 
}
