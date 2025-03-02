package views;

import views.view_models.RecipeViewModel;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

//extends StackPane to use z-indexing of elements
public class RecipeView extends StackPane{

    private Label lblRecipeName, lblUserMessage;
    private TextField tfRecipeName;
    private Button btnSaveRecipeName;
    private HBox hboxRecipeNameInput, hboxRecipeNameLabel;
    private VBox vboxInputContainer, vboxLabelContainer;
    private boolean recipeNameToggle;

    private final Font smallFont = new Font("Arial", 12);
    private final Font mediumFont = new Font("Arial", 24);
    private final Font largeFont = new Font("Arial", 36);
    
    private final RecipeViewModel recipeViewModel = new RecipeViewModel();

    public RecipeView(){       
        createNameRecipeView();
        bindViewModel(); 
    }
 
    private void createNameRecipeView(){
        this.recipeNameToggle = true;

        this.lblUserMessage = new Label("Click to rename your recipe!");
        this.lblRecipeName = new Label("");
        // this.lblRecipeName2 = new Label("Recipe Name 2");        
        this.tfRecipeName = new TextField();
        this.btnSaveRecipeName = new Button("Save");

        this.lblUserMessage.setFont(this.smallFont);
        this.lblRecipeName.setFont(this.largeFont);
        // this.lblRecipeName2.setFont(this.font);
        this.tfRecipeName.setFont(this.largeFont);
        this.btnSaveRecipeName.setFont(this.mediumFont);

        this.lblRecipeName.setStyle("-fx-padding: 12 8 8 8");
        // this.tfRecipeName.setStyle("-fx-alignment: center");
        this.tfRecipeName.setAlignment(Pos.CENTER);
        this.btnSaveRecipeName.setAlignment(Pos.CENTER);


        this.lblUserMessage.setOnMouseClicked(this::swapLayer);
        this.lblRecipeName.setOnMouseClicked(this::swapLayer);
        this.tfRecipeName.setOnKeyPressed(this::processKeyPress);
        this.btnSaveRecipeName.setOnAction(this::save);
        
        this.hboxRecipeNameInput = new HBox(); 
        this.hboxRecipeNameLabel = new HBox(); 

        this.vboxInputContainer = new VBox();
        this.vboxLabelContainer = new VBox();
      

        /*********************************User input screen*********************************/
    
        //TODO: This may be easier to do with a GridPane        
        //Hbox container for textfield for user input   
        this.hboxRecipeNameInput.setSpacing(10);
        this.hboxRecipeNameInput.setAlignment(Pos.CENTER);
        this.hboxRecipeNameInput.setStyle("-fx-background-color: #fccdb6");        
        this.hboxRecipeNameInput.getChildren().addAll(this.tfRecipeName);        
        this.hboxRecipeNameInput.setPrefWidth(900);
        HBox.setHgrow(this.tfRecipeName, Priority.ALWAYS);

        VBox separatorNameInput = new VBox();
        separatorNameInput.getChildren().addAll(this.hboxRecipeNameInput, this.btnSaveRecipeName);
        separatorNameInput.setAlignment(Pos.TOP_CENTER);
        separatorNameInput.setPrefHeight(150);
        separatorNameInput.setStyle("-fx-background-color: #fccdb6");
        
        //Vbox to hold Hbox to user input textfield
        this.vboxInputContainer.setPrefHeight(300);
        this.vboxInputContainer.setAlignment(Pos.CENTER);
        this.vboxInputContainer.setStyle("-fx-background-color: #fccdb6");
        this.vboxInputContainer.getChildren().addAll(separatorNameInput);

        /*********************************Input display screen*********************************/

        //Hbox container that displays user input via label             
        this.hboxRecipeNameLabel.setSpacing(10);
        this.hboxRecipeNameLabel.setAlignment(Pos.CENTER);
        this.hboxRecipeNameLabel.setStyle("-fx-background-color: #fccdb6");
        this.hboxRecipeNameLabel.getChildren().addAll(this.lblRecipeName);        
        this.hboxRecipeNameLabel.setPrefWidth(900);

        VBox separatorNameLabel = new VBox();
        separatorNameLabel.getChildren().addAll(this.hboxRecipeNameLabel, this.lblUserMessage);
        separatorNameLabel.setAlignment(Pos.TOP_CENTER);
        separatorNameLabel.setPrefHeight(150);        
        separatorNameLabel.setStyle("-fx-background-color: #fccdb6)");
        
        //Vbox to hold Hbox user input label     
        this.vboxLabelContainer.setPrefHeight(300);
        this.vboxLabelContainer.setAlignment(Pos.CENTER);
        this.vboxLabelContainer.setStyle("-fx-background-color: #fccdb6");
        this.vboxLabelContainer.getChildren().addAll(separatorNameLabel);

        //initial display
        this.getChildren().addAll(this.vboxLabelContainer, this.vboxInputContainer);
    }
    //SwapLayer being called by a MouseEvent 
    private void swapLayer(MouseEvent event){
        swap();
    }

    //SwapLayer being called by a KeyEvent 
    private void swapLayer(KeyEvent event){
        swap();
    }

    //SwapLayer being called by a ActionEvent
    private void swapLayer(ActionEvent event){
        swap();
    }

    //only works for two items, rework for 3 and above to use in application function traversal
    private void swap(){

        /**/
        //swap logic that checks for the top most element
        // if (this.getChildren().getLast() == this.vboxInputContainer){
        //     System.out.println("You are on the input view, now swapping to label view.");           
        //     this.getChildren().clear();
        //     this.getChildren().addAll(this.vboxInputContainer, this.vboxLabelContainer);
        //     return;
        // }
        // System.out.println("You are on the label view, now swapping to input view.");   
        // this.getChildren().clear();;
        // this.getChildren().addAll(this.vboxLabelContainer, this.vboxInputContainer);
        /**/

        /**/
        //swap logic that checks against a boolean (addtional boolean attribute required), limited to two elements, may be perfect for usage here.
        if(recipeNameToggle){
            fadeOut(this.vboxInputContainer);
            fadeIn(this.vboxLabelContainer);
            recipeNameToggle = !recipeNameToggle;            
        }else{
            fadeOut(this.vboxLabelContainer);
            fadeIn(this.vboxInputContainer);
            recipeNameToggle = !recipeNameToggle;  
        }
        /**/
    }

    private void fadeIn(VBox ref) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), ref);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();        
    }

    private void fadeOut(VBox ref) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), ref);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void bindViewModel(){
        this.tfRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty());
        this.lblRecipeName.textProperty().bindBidirectional(recipeViewModel.nameProperty()); 
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
