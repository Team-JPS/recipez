package views;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import models.POJO.Recipe;
import util.GlobalValues;
import views.view_models.RecipeBookViewModel;

//This extends StackPane, but you can change it to whatever UI element works best for your View Layout.
public class RecipeBookView extends StackPane {

    //properties
    private Label lblRecipeName;
    private ComboBox<String> sortDropdown;
    private HBox hboxRecipeBook;
    private VBox vboxRecipeBookContainer;
    private TableView<Recipe> tableView;
    

    private final RecipeBookViewModel recipeBookViewModel = new RecipeBookViewModel();
    private int currentRecipeIndex = 0;
    private ArrayList<Recipe> recipeList;
    
    public RecipeBookView() {
       //createView() will populate the UI.

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
        sortDropdown.getItems().addAll("Sort by Name (A-Z)", "Sort by Name (Z-A)");
        sortDropdown.setValue("Sort by Name (A-Z)");

        sortDropdown.setOnAction(e -> applySorting());

        hboxRecipeBook = new HBox(sortDropdown);

        // creating table view
        tableView = new TableView<>();

        TableColumn<Recipe, String> nameColumn = new TableColumn<>("Recipe Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("recipeName")); 

        tableView.getColumns().add(nameColumn);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        vboxRecipeBookContainer = new VBox(hboxRecipeBook, tableView);
        this.getChildren().add(vboxRecipeBookContainer);
    }

    private void loadRecipes() {
        recipeList = recipeBookViewModel.getRecipeBook();
        tableView.getItems().setAll(recipeList); // Ensure this method exists in RecipeBookViewModel
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


    
}
