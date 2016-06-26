package guiDM;

import appCore.dataMapper.Medicament;
import appCore.dataMapper.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class RecipeAddControllerDM implements Initializable {

    @FXML
    public TextField cashRegisterNumberField;

    @FXML
    public TextField recipeNumberField;

    @FXML
    public TextField recipeTypeField;

    @FXML
    public TextField medicamentField;

    @FXML
    public TextArea errorLog;

    public Recipe insertedRecipe = null;

    public void handleAddRecipeButtonAction(ActionEvent event){
        if (!validateInputs())
            return;
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Recipe recipe = new Recipe();
        entityManager.persist(recipe);
        recipe.cashRegisterNumber = Integer.parseInt(cashRegisterNumberField.getText());
        recipe.number = Integer.parseInt(recipeNumberField.getText());
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Date date = new Date();
        recipe.date = new java.sql.Date(date.getTime());
        recipe.type = recipeTypeField.getText();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("find by title");
        query.setParameter("title", medicamentField.getText());

        try {
            Medicament medicament = (Medicament) query.getSingleResult();
            if (medicament != null)
            recipe.medicament = medicament;
        } catch (NoResultException e){
            Medicament medicament = new Medicament();
            entityManager.persist(medicament);
            recipe.medicament = medicament;
        }
        insertedRecipe = recipe;
        entityManager.getTransaction().commit();

        cashRegisterNumberField.getScene().getWindow().hide();
    }

    public boolean validateInputs(){
        boolean valid = true;

        try{
            Integer.parseInt(cashRegisterNumberField.getText());
        }catch (Exception e){
            errorLog.appendText("Zle zadane cislo pokladne!\n");
            valid = false;
        }

        try{
            Integer.parseInt(cashRegisterNumberField.getText());
        }catch (Exception e){
            errorLog.appendText("Zle zadane cislo receptu!\n");
            valid = false;
        }

        if (recipeTypeField.getText().isEmpty()){
            errorLog.appendText("Ty receptu nesmie byt prazdny!\n");
            valid = false;
        }

        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("find by title");
        query.setParameter("title", medicamentField.getText());
        entityManager.getTransaction().commit();

        try {
            Medicament medicament = (Medicament) query.getSingleResult();

        }catch (NoResultException e){
            errorLog.appendText("Liek neexsituje v databze\n");
            valid = false;
        }

        return valid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
