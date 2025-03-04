package views.view_models;

// import javafx.beans.property.BooleanProperty;
// import javafx.beans.property.ReadOnlyStringProperty;
// import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.RecipeModel;
import models.POJO.Recipe;
import util.ViewToModelConverter;

// 
public class RecipeViewModel {
    /*
     * 
     */
    
    //Data elements
    private final StringProperty recipeName = new SimpleStringProperty();
    
    //Uses data from this viewModel to create a new Recipe class.
    private final ViewToModelConverter converter = new ViewToModelConverter();
    
    private final RecipeModel recipeModel = new RecipeModel();

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
