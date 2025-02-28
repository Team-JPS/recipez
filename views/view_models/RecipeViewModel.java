package views.view_models;

// import javafx.beans.property.BooleanProperty;
// import javafx.beans.property.ReadOnlyStringProperty;
// import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.RecipeModel;
import models.POJO.Recipe;
import util.RecipeConverter;

// 
public class RecipeViewModel {
    /*
     * 
     */
    
    //Data elements
    private final StringProperty recipeName = new SimpleStringProperty();
    
    //Uses data from this viewModel to create a new Recipe class.
    private final RecipeConverter converter = new RecipeConverter();
    
    private final RecipeModel recipeModel = new RecipeModel();

    
    public String getName(){
        if(recipeName.get() == null || recipeName.get().trim() == ""){
            return "Big ol weiners";
        }
        return recipeName.get();
    }

    public StringProperty nameProperty(){
        return recipeName;
    } 
    
    public void setName(String name){
        this.recipeName.set(name);
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
