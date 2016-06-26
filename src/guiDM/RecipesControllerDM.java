package guiDM;

import appCore.dataMapper.Medicament;
import appCore.dataMapper.Recipe;
import appCore.dataMapper.RecipesBatch;
import appCore.domainModel.Selects;
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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecipesControllerDM implements Initializable{

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
    private List<appCore.dataMapper.Recipe> recipeList;



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

        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        recipe = entityManager.find(Recipe.class, recipe.recipeID);
        Query query = entityManager.createNamedQuery("find by number", RecipesBatch.class);
        query.setParameter("number", recipeBatchNumber);
        try {
            RecipesBatch recipesBatch = (RecipesBatch) query.getSingleResult();
            recipe.recipesBatch = recipesBatch;
        } catch (NoResultException e){
            System.out.println("Except");
            RecipesBatch recipesBatch = new RecipesBatch();
            entityManager.persist(recipesBatch);
            recipesBatch.number = Integer.parseInt(batchNumberField.getText());
            recipe.recipesBatch = recipesBatch;
        }
        entityManager.getTransaction().commit();

    }


    public void handleAddRecipeButtonAction(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeAddDM.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            RecipeAddControllerDM recipeAddController = loader.<RecipeAddControllerDM>getController();

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
        Selects selects = new Selects();
        recipeList = selects.getAllRecipes();
        observableRecipeList = FXCollections.observableArrayList(recipeList);
        recipesView.setItems(observableRecipeList);
        if (!observableRecipeList.isEmpty())
            recipesView.getSelectionModel().selectFirst();
        updateLabels(recipesView.getSelectionModel().getSelectedItem());


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
        if (recipe.date != null)
            dateLabel.setText(recipe.date.toString());
        else
            dateLabel.setText("");
        recipeNumberLabel.setText(String.valueOf(recipe.number));
        if (recipe.type != null)
            typeLabel.setText(recipe.type);
        else
            typeLabel.setText("");


        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();

        if (recipe.medicament != null) {
            Medicament medicament = entityManager.find(Medicament.class, recipe.medicament.medicamentId);
            if (medicament != null) {
                medicamentLabel.setText(medicament.toString());
            } else
                medicamentLabel.setText("");
        }

        if (recipe.recipesBatch != null) {
            RecipesBatch recipesBatch = entityManager.find(RecipesBatch.class, recipe.recipesBatch.recipeBatchId);
            if (recipesBatch != null) {
                batchLabel.setText(recipesBatch.toString());
            } else
                batchLabel.setText("");
        }

        entityManager.getTransaction().commit();
    }
}
