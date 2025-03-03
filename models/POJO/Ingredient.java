package models.POJO;

public class Ingredient {
    // TODO: JavaDoc this class

    /** 
     * @param ingredientName will intialize with a name created by user input
     *  typing out a name, or selecting a name from a list of suggestions (types a litter in and gets suggestions).
     * @param quantity is for shopping list usage, default to 1.      
     * @param measurement of food iteam in recipe (1, 1/2, 1/4, 1/8) to use in recipe
    **/ 
    
   /**
     * For use with nutrition tracker addon
     * 
     * private int fat;
     * private int oil;
     * private int protien;
     * private int sugar;
     * more...
     * 
     * **/

    private String ingredientName;
    private int quantity; 
    private String measurement;
     
    public Ingredient(String name) {      
        this.ingredientName = name;
        this.quantity = 1;
        this.measurement = "1";
    }

    // Add Setters and Getters
}
