package guiTS;

import appCore.transactionScript.rowGateways.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AddWindowControllerTS implements Initializable {
    @FXML
    TextField titleField;

    @FXML
    TextField batchField;

    @FXML
    TextField soldField;

    @FXML
    TextField stateField;

    @FXML
    TextField codeField;

    @FXML
    TextField addedField;

    @FXML
    TextField expirationField;

    @FXML
    TextField saleCategoriesField;

    @FXML
    TextField medicamentCategoriesField;

    @FXML
    TextField sellingPriceField;

    @FXML
    TextField buyoutPriceField;

    @FXML
    TextArea errorsLog;

    Medicament insertedMedicament;

    public Medicament getMedicament() {
        return insertedMedicament;
    }

    /**
     * veryfi and saves given information to the database
     */
    public void handleOkButtonAction(ActionEvent event){
        if (!verifyInputs()){
            return;
        }
        Price price = new Price();
        price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
        price.sellingPrice = new BigDecimal(sellingPriceField.getText());
        price.insert();

        MedicamentInformation medicamentInformation = new MedicamentInformation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date expirationDate = dateFormat.parse(expirationField.getText());
            medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
            Date soldDate = dateFormat.parse(soldField.getText());
            medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
            Date buyoutDate = dateFormat.parse(addedField.getText());
            medicamentInformation.added = new java.sql.Date(buyoutDate.getTime());
            medicamentInformation.insert();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        // inserts new state only if this state don't exists already
        State state = State.findByTitle(stateField.getText());
        if (state == null) {
            state = new State();
            state.title = stateField.getText();
            state.insert();
        }


        // inserts new category only if desired category doesn't exists
        MedicamentCategory medicamentCategory = MedicamentCategory.findByTitle(medicamentCategoriesField.getText());
        if (medicamentCategory == null){
            medicamentCategory = new MedicamentCategory();
            medicamentCategory.title = medicamentCategoriesField.getText();
            medicamentCategory.insert();
        }




        // inserts new sale category only if this category doesnt exists
        SaleCategory saleCategory = SaleCategory.findByName(saleCategoriesField.getText());
        if (saleCategory == null) {
            saleCategory = new SaleCategory();
            saleCategory.title = saleCategoriesField.getText();
            saleCategory.insert();
        }


        // finaly inserts new medicamentCategories with all foreign keys and so on

        Medicament medicament = new Medicament();
        medicament.title = titleField.getText();
        medicament.batch = batchField.getText();
        medicament.code = codeField.getText();
        medicament.medicamentInformationID = medicamentInformation.medicamentInformationID;
        medicament.priceID = price.priceID;
        medicament.saleCategoryId = saleCategory.saleCategoryID;
        medicament.stateID = state.stateID;
        medicament.insert();

        // at the end update binding table
        InMedicamentCategory inMedicamentCategory = new InMedicamentCategory();
        inMedicamentCategory.medicament_category_id = medicamentCategory.medicamentCategoryID;
        inMedicamentCategory.medicament_id = medicament.medicamentID;
        inMedicamentCategory.insert();
        System.out.println("inserted");
        System.out.println(medicamentCategory.medicamentCategoryID);
        System.out.println(medicament.medicamentID);


        insertedMedicament = medicament;
        titleField.getScene().getWindow().hide();
    }


    private boolean verifyInputs(){
        errorsLog.setText("");
        boolean valid = true;

        if (titleField.getText().isEmpty()){
            errorsLog.appendText("Nazov nesmie byt prazdny\n");
            valid = false;
        }

        if (codeField.getText().isEmpty()){
            errorsLog.appendText("Kazdy liek musi obsahovat kod\n");
            valid = false;
        }

        if (batchField.getText().isEmpty()){
            errorsLog.appendText("Kazdy liek musi byt v nejakej davke\n");
            valid = false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            dateFormat.parse(expirationField.getText());
            dateFormat.parse(addedField.getText());
            dateFormat.parse(soldField.getText());
        } catch (ParseException e){
            errorsLog.appendText("Zle zadany format datumu\n");
            valid = false;
        }

        if (saleCategoriesField.getText().isEmpty()){
            errorsLog.appendText("predajna kateogria nesmie byt prazdna\n");
            valid = false;

        }

        if (medicamentCategoriesField.getText().isEmpty()){
            errorsLog.appendText("kategoria lieku nesmie byt prazdna\n");
            valid = false;

        }

        if (stateField.getText().isEmpty()){
            errorsLog.appendText("liek musi mat stav\n");
            valid = false;

        }

        if (buyoutPriceField.getText().isEmpty()){
            errorsLog.appendText("nakupna cena musi byt vyplnena\n");
            valid = false;

        }

        if (sellingPriceField.getText().isEmpty()){
            errorsLog.appendText("predajna cena musi byt vyplnena\n");
            valid = false;

        }

        try{
            BigDecimal bigDecimal = new BigDecimal(buyoutPriceField.getText());
            bigDecimal = new BigDecimal(sellingPriceField.getText());
        }catch (NumberFormatException e){
            errorsLog.appendText("Zle zadana suma\n");
            valid = false;

        }

        return valid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
