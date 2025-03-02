package views;

import views.view_models.RecipeViewModel;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

//extends StackPane to use z-indexing of elements
public class RecipeView extends StackPane{

    private Label lblRecipeName, lblRecipeName2, lblUserMessage;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName;
    private HBox hboxRecipeNameInput, hboxRecipeNameLabel;
    private VBox vboxInputContainer, vboxLabelContainer;

    private final Font font = new Font("Arial", 24);
        
    private final RecipeViewModel recipeViewModel = new RecipeViewModel();

    public RecipeView(){       
        createNameRecipeView();
        bindViewModel(); 
    }
 
    private void createNameRecipeView(){

        this.lblUserMessage = new Label("Name your Recipe.");
        this.lblRecipeName = new Label("Recipe Name 1");
        this.lblRecipeName2 = new Label("Recipe Name 2");        
        this.tfRecipeName = new TextField();
        this.btnSaveRecipeName = new Button("Save");

        this.lblRecipeName.setFont(this.font);
        this.lblRecipeName2.setFont(this.font);
        this.tfRecipeName.setFont(this.font);
        this.btnSaveRecipeName.setFont(this.font);

        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::save);
        this.lblRecipeName2.setOnMouseClicked(this::swapLayer);

        this.hboxRecipeNameInput = new HBox(); 
        this.hboxRecipeNameLabel = new HBox(); 

        this.vboxInputContainer = new VBox();
        this.vboxLabelContainer = new VBox();
      
        //Hbox container for textfield for user input            
        hboxRecipeNameInput.setSpacing(10);
        hboxRecipeNameInput.setAlignment(Pos.CENTER);
        hboxRecipeNameInput.setStyle("-fx-background-color: #fccdb6");
        hboxRecipeNameInput.getChildren().addAll(this.lblRecipeName, this.tfRecipeName, this.btnSaveRecipeName);
        hboxRecipeNameInput.setPrefWidth(900);
        HBox.setHgrow(this.tfRecipeName, Priority.ALWAYS);        
        //Vbox to hold Hbox to user input textfield
        vboxInputContainer.setPrefHeight(300);
        vboxInputContainer.setAlignment(Pos.CENTER);
        vboxInputContainer.setStyle("-fx-background-color: #fccdb6");
        vboxInputContainer.getChildren().addAll(hboxRecipeNameInput);

        //Hbox container that displays user input via label             
        hboxRecipeNameLabel.setSpacing(10);
        hboxRecipeNameLabel.setAlignment(Pos.CENTER_LEFT);
        hboxRecipeNameLabel.setStyle("-fx-background-color: #fccdb6");
        hboxRecipeNameLabel.getChildren().addAll(this.lblRecipeName2, this.lblUserMessage);
        hboxRecipeNameLabel.setPrefWidth(900);
        //Vbox to hold Hbox user input label     
        vboxLabelContainer.setPrefHeight(300);
        vboxLabelContainer.setAlignment(Pos.CENTER);
        vboxLabelContainer.setStyle("-fx-background-color: #fccdb6");
        vboxLabelContainer.getChildren().addAll(hboxRecipeNameLabel);

        //initial display
        this.getChildren().addAll(vboxLabelContainer, vboxInputContainer);
    }
    //SwapLayer being called by a mouseevent 
    private void swapLayer(MouseEvent event){
        swap();
    }

    //SwapLayer will be called by a Mouseevent 
    private void swapLayer(KeyEvent event){
        swap();
    }

    //SwapLayer will be called by a ActionEvent
    private void swapLayer(ActionEvent event){
        swap();
    }

    private void swap(){
        if (this.getChildren().getLast() == this.vboxInputContainer){
            System.out.println("You are on the input view, now swapping to label view.");
            this.getChildren().clear();;
            this.getChildren().addAll(this.vboxInputContainer, this.vboxLabelContainer);
            return;
        }
        System.out.println("You are on the label view, now swapping to input view.");
        this.getChildren().clear();;
        this.getChildren().addAll(this.vboxLabelContainer, this.vboxInputContainer);
    }



    private void bindViewModel(){
        this.tfRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty());
        this.lblRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty());       
        this.lblRecipeName2.textProperty().bindBidirectional(recipeViewModel.nameProperty()); 
    }    

    private void processKeyPress(KeyEvent event){
       switch(event.getCode()){
            case ENTER:
                save(event);
                swapLayer(event);
                break;
            default:
       } 
    }
 

    private void save(ActionEvent evt){ recipeViewModel.save() ;swapLayer(evt); }
    private void save(KeyEvent evt){ recipeViewModel.save(); }
}
