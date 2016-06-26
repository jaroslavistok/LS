package guiTS;

import appCore.transactionScript.rowGateways.Medicament;
import appCore.transactionScript.rowGateways.Recipe;
import appCore.transactionScript.rowGateways.RecipesBatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecipesControllerTS implements Initializable{

    @FXML
    public ListView<Recipe> recipesView;

    @FXML
    public TextField batchNumberField;


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

    @FXML
    public TextArea errorLog;
    private ObservableList<Recipe> observableRecipeList;
    private List<Recipe> recipeList;



    /**
     * retax button event handler,
     */
    public void handleRetaxButton(ActionEvent event){
        Recipe recipe = recipesView.getSelectionModel().getSelectedItem();


        int recipeBatchNumber = -1;
        try {
            recipeBatchNumber = Integer.parseInt(batchNumberField.getText());
        }catch (Exception e){
            errorLog.appendText("Zle zadane cislo davky receptu");
            return;
        }

        RecipesBatch recipesBatch = RecipesBatch.findByNumber(recipeBatchNumber);
        if (recipesBatch != null){
            recipe.recipeBatchID = recipesBatch.recipeBatchId;
            recipe.update();
        } else {
            recipesBatch = new RecipesBatch();
            recipesBatch.number = Integer.parseInt(batchNumberField.getText());
            recipesBatch.insert();

            recipe.recipeBatchID = recipesBatch.recipeBatchId;
            recipe.update();
        }
    }


    public void handleAddRecipeButtonAction(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeAddTS.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            RecipeAddControllerTS recipeAddController = loader.<RecipeAddControllerTS>getController();

            stage.setTitle("Pridavanie receptov");
            stage.setScene(new Scene(root, 370, 398));
            stage.showAndWait();

            if (recipeAddController.insertedRecipe != null){
                observableRecipeList.add(recipeAddController.insertedRecipe);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Loads and set all recipes to the list view in gui
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recipeList = Recipe.getAllRecipes();
        observableRecipeList = FXCollections.observableArrayList(recipeList);
        recipesView.setItems(observableRecipeList);

        recipesView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);

            if (newValue != null)
                updateLabels(newValue);

        });
    }

    private void updateLabels(Recipe recipe){
        recipeNumberLabel.setText("");
        cashRegisterNumberLabel.setText("");
        dateLabel.setText("");
        typeLabel.setText("");
        medicamentLabel.setText("");
        batchLabel.setText("");

        cashRegisterNumberLabel.setText(String.valueOf(recipe.cashRegisterNumber));
        dateLabel.setText(recipe.date.toString());
        recipeNumberLabel.setText(String.valueOf(recipe.number));
        typeLabel.setText(recipe.type);

        Medicament medicament = Medicament.findByID(recipe.medicamentID);
        if (medicament != null){
            medicamentLabel.setText(medicament.toString());
        }

        RecipesBatch recipesBatch = RecipesBatch.findById(recipe.recipeBatchID);
        if (recipesBatch != null){
            batchLabel.setText(recipesBatch.toString());
        }
    }
}
