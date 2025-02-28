package views;

import views.view_models.RecipeViewModel;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RecipeView extends StackPane{
    private Label lblRecipeName;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName;
    private final Font font = new Font("Arial", 24);;
        
    private final RecipeViewModel recipeViewModel = new RecipeViewModel();

    public RecipeView(){       
        createView();
        bindViewModel(); 
    }
 
    private void createView(){

        this.lblRecipeName = new Label("Name of Recipe:");        
        this.tfRecipeName = new TextField();
        this.btnSaveRecipeName = new Button("Save");

        this.lblRecipeName.setFont(this.font);
        this.tfRecipeName.setFont(this.font);
        this.btnSaveRecipeName.setFont(this.font);

        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::save);
      
        HBox hboxRecipeNameHolder = new HBox();
        hboxRecipeNameHolder.setSpacing(10);
        hboxRecipeNameHolder.getChildren().addAll(this.lblRecipeName, this.tfRecipeName, this.btnSaveRecipeName);
        hboxRecipeNameHolder.setAlignment(Pos.CENTER);
        hboxRecipeNameHolder.setStyle("-fx-background-color: crimson");
      
        VBox container = new VBox();
        container.getChildren().addAll(hboxRecipeNameHolder);

        this.getChildren().addAll(container);
    }

    private void bindViewModel(){
        this.tfRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty());
    }    

    private void processKeyPress(KeyEvent event){
       switch(event.getCode()){
            case ENTER:
                save(event);
                break;
            default:
       } 
    }

    private void save(ActionEvent evt){ recipeViewModel.save(); }
    private void save(KeyEvent evt){ recipeViewModel.save(); }
}
