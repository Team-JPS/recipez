package com.recipez.models.POJO;

public class Ingredient {
    // TODO: JavaDoc this class

    /** 
     * @param ingredientName will intialize with a name created by user input
     *  typing out a name, or selecting a name from a list of suggestions (types a litter in and gets suggestions).
     * @param quantity is for shopping list usage, default to 1.      
     * @param volume of food iteam in recipe (1, 1/2, 1/4, 1/8) to use in recipe
     * @param unitOfVulume cup(c), teaspoon(tsp), tablespoont(tbsp), gallon(gal), quart(qt), pint(pt)
     * may become an emum
     * @param weight if measurement is not in volume but weight, use this property
     * @param unitOfWeight pound(lb), Ounce (oz),  
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

    private String ingredientName, volume, unitOfVolume, weight, unitOfWeight;
    private int quantity; 
     
    public Ingredient(String name) {      
        this.ingredientName = name;
        this.quantity = 1; 
        this.volume = "1";  
        this.unitOfVolume = "cup";  
        this.weight = "0"; 
        this.unitOfWeight = "pounds";  
    }

    public String getName(){
        return this.ingredientName;
    }

    public void setName(String name){
        this.ingredientName = name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

     // Measurement may become an enum, possible change.. 
    public String getVolume(){
        return this.volume;
    }

    public void setVolume(String volume){
        this.volume = volume;
    }

    // Unit of volume may become an enum, possible change.. 
    public String getUnitOfVolume(){
        return this.unitOfVolume;
    }

    public void setUnitOfVolume(String unitOfVolume){
        this.unitOfVolume = unitOfVolume;
    }

    public String getWeight(){
        return this.weight;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public String getUnitOfWeight(){
        return this.unitOfWeight;
    }

    public void setUnitOfWeight(String unitOfWeight){
        this.unitOfWeight = unitOfWeight;
    }

    // Add Setters and Getters
}
