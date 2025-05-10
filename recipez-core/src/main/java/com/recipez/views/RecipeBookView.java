package com.recipez.views;
import java.util.ArrayList;
import java.util.Comparator;

import com.recipez.models.POJO.Recipe;
import com.recipez.models.RecipeDataStoreModel;
import com.recipez.util.CurrentUpdate;
import com.recipez.util.GlobalValues;
import com.recipez.util.Observer;
import com.recipez.util.Subject;
import com.recipez.views.view_models.RecipeBookViewModel;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//This extends StackPane, but you can change it to whatever UI element works best for your View Layout.
public class RecipeBookView extends StackPane implements Observer {

    //properties
    private Label lblRecipeName;
    private ComboBox<String> sortDropdown;
    private HBox hboxRecipeBook;
    private VBox vboxRecipeBookContainer;
    private TableView<Recipe> tableView;
     

    private final RecipeBookViewModel recipeBookViewModel = new RecipeBookViewModel();
    private Subject dataStoreUpdater;
    
    private int currentRecipeIndex = 0;
    private ArrayList<Recipe> recipeList;
    
    public RecipeBookView(Subject dataStoreUpdater) {
       //createView() will populate the UI.
       dataStoreUpdater.registerObserver(this);
       this.dataStoreUpdater = dataStoreUpdater;
       createView();
       loadRecipes();
       
    }

    public void createView(){
        //intialize the properties and Create the UI for recipe Book here. Any data you need to 
        //display will come from this.recipeBookViewModel
        this.setStyle(GlobalValues.COLOR_TEST_FORMATTING_TWO);
        this.setWidth(GlobalValues.APP_WIDTH);
        this.setHeight(GlobalValues.VIEW_HEIGHT);
        this.setMinHeight(GlobalValues.VIEW_HEIGHT);

        sortDropdown = new ComboBox<>();
        sortDropdown.getItems().addAll("Sort by Name (A-Z)", "Sort by Name (Z-A)", 
        "Sort by Protein Type (A-Z)", "Sort by Protein Type (Z-A)", 
        "Sort by Ethnicity(A-Z)", "Sort by Ethnicity(Z-A)", 
        "Sort by Cooking Time (Low to High)", "Sort by Cooking Time (High to Low)");
        sortDropdown.setValue("Sort by Name (A-Z)");

        sortDropdown.setOnAction(e -> applySorting());

        hboxRecipeBook = new HBox(sortDropdown);

        // creating table view
        tableView = new TableView<>();

        TableColumn<Recipe, String> nameColumn = new TableColumn<>("Recipe Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("recipeName")); 

        nameColumn.setCellFactory(column -> {
        TableCell<Recipe, String> cell = new TableCell<Recipe, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    } else {
                        setText(item);
                        setOnMouseClicked(event -> {
                        showRecipeDetails(getTableRow().getItem());
                        });
                    }
                }
            };
            return cell;
        });


        tableView.getColumns().add(nameColumn);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        VBox recipeDetails = new VBox();
        recipeDetails.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px;");
        this.getChildren().add(recipeDetails);
        
        // vboxRecipeBookContainer = new VBox(hboxRecipeBook, tableView);
        // this.getChildren().add(vboxRecipeBookContainer);
    }

    private void loadRecipes() {
        recipeList = recipeBookViewModel.getRecipeBook();
        tableView.getItems().setAll(recipeList); // Ensure this method exists in RecipeBookViewModel
    }

    private void showRecipeDetails(Recipe recipe) {
        // Implement the logic to show recipe details
        // You can create a new view or update the existing one with the recipe details
        System.out.println("Selected Recipe: " + recipe.getRecipeName());
    }

    private void applySorting() {
        String selectedSort = sortDropdown.getValue();

        if (selectedSort.equals("Sort by Name (A-Z)")) {
            recipeList.sort(Comparator.comparing(Recipe::getRecipeName));
        } else if (selectedSort.equals("Sort by Name (Z-A)")) {
            recipeList.sort(Comparator.comparing(Recipe::getRecipeName).reversed());
        }
        tableView.getItems().setAll(recipeList);
    }

    
    public void update(CurrentUpdate update){
        switch(update){
            case NONE:
            break;
            case RECIPE:
                System.out.println("RecipeBookView is receiving updates from CreateRecipeView");
                recipeBookViewModel.loadRecipe();
                tableView.getItems().clear();
                this.loadRecipes();
                ((RecipeDataStoreModel)this.dataStoreUpdater).setUpdate(CurrentUpdate.NONE);
            break;
            case GROCERY:
            break;
            default:
            break;
        }
    }
    
}
