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
     * @param unitOfWeight pound(lb), ounce (oz),  
     * 
     * Some of these parameters may become enums. try and work this in.
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
    private String quantity; 
     

    // There are multiple constructors as I work with adding/editing a new property
    // I dont want to break what currently works. So as I work with a new property I will add a new constructor
    // with the new incoming property. 

    // When all properties are being added/edited properly the final constructor should be the only one left. 
    public Ingredient(String name) {      
        this.ingredientName = name;
        this.quantity = "1"; 
        this.volume = "1";  
        this.unitOfVolume = "cup";  
        this.weight = "0"; 
        this.unitOfWeight = "ounces";  
    }

    public Ingredient(String name, String volume) {      
        this.ingredientName = name;
        this.quantity = "1"; 
        this.volume = volume;  
        this.unitOfVolume = "cup";  
        this.weight = "0"; 
        this.unitOfWeight = "ounces";  
    }

    public Ingredient(String name, String quantity, String volume, String unitOfVolume, String weight, String unitOfWeight) {      
        this.ingredientName = name;
        this.quantity = quantity; 
        this.volume = volume;  
        this.unitOfVolume = unitOfVolume;  
        this.weight = weight; 
        this.unitOfWeight = unitOfWeight;  
    }



    public String getIngredientName(){
        return this.ingredientName;
    }

    public void setName(String name){
        this.ingredientName = name;
    }

    public String getQuantity(){
        return this.quantity;
    }

    //this is going to be a String, unless it needs to be an int. For sake of ease, String.
    public void setQuantity(String quantity){
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

}
