package models.POJO;

public class Recipe {
    // TODO: JavaDoc this class
 
    private String recipeName;  
          
    public Recipe(String name){        
        this.recipeName = name;
    }

    public String getRecipeName(){
        return this.recipeName;
    }

    public void setRecipeName(String name){
        this.recipeName = name;
    }

    public String toString(){
        return "Recipe Information:\nName: "+this.recipeName;
    }

}
