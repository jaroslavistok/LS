package guiTS;

import appCore.transactionScript.rowGateways.Medicament;
import appCore.transactionScript.rowGateways.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class RecipeAddControllerTS implements Initializable {

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

        Recipe recipe = new Recipe();
        recipe.cashRegisterNumber = Integer.parseInt(cashRegisterNumberField.getText());
        recipe.number = Integer.parseInt(recipeNumberField.getText());

        // sets the current time
        Date date = new java.util.Date();
        recipe.date = new java.sql.Date(date.getTime());
        recipe.type = recipeTypeField.getText();

        Medicament medicament = Medicament.findBuTitle(medicamentField.getText());
        if (medicament != null)
            recipe.medicamentID = medicament.medicamentID;
        recipe.insert();
        insertedRecipe = recipe;
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

        Medicament medicament = Medicament.findBuTitle(medicamentField.getText());
        if (medicament == null){
            errorLog.appendText("Liek neexsituje v databze\n");
            valid = false;
        }

        return valid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
