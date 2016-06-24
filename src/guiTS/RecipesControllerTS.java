package guiTS;

import appCore.transactionScript.rowGateways.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecipesControllerTS implements Initializable{

    @FXML
    public ListView<Recipe> recipesView;


    @FXML
    public Label recipeNumberLabel;

    @FXML
    public Label cashRegisterNumberLabel;

    @FXML
    public Label dateLabel;

    @FXML
    public Label typeLabel;

    @FXML
    public Label medicamentLabel;

    @FXML
    public Label batchLabel;


    /**
     * Change button event handler, changes selected recipe
     *
     */
    public void handleChangeBatchButton(ActionEvent event){

    }

    /**
     * retax button event handler,
     */
    public void handleRetaxButton(ActionEvent event){
        List<Recipe> recipes = recipesView.getSelectionModel().getSelectedItems();



        for (Recipe recipe : recipes){


        }

    }


    /**
     * Loads and set all recipes to the list view in gui
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Recipe> recipeList = Recipe.getAllRecipes();
        ObservableList<Recipe> observableRecipeList =  FXCollections.observableArrayList(recipeList);
        recipesView.setItems(observableRecipeList);
    }
}
