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

public class UpdateControllerTS implements Initializable {

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



    int medicamentID;

    int saleCategoryID;

    int medicamentCategoryID;

    int priceID;

    int medicamentInformationID;

    int stateID;

    Medicament updated;

    Medicament toUpdate;

    public void handleUpdateButtonAction(ActionEvent event){
        if (!verifyInputs())
            return;

        if (toUpdate != null) {

            toUpdate.title = titleField.getText();
            toUpdate.code = codeField.getText();
            toUpdate.batch = batchField.getText();

            MedicamentInformation medicamentInformation = MedicamentInformation.findByID(toUpdate.medicamentInformationID);
            if (medicamentInformation != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date expirationDate = simpleDateFormat.parse(expirationField.getText());
                    medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
                    Date addedDate = simpleDateFormat.parse(addedField.getText());
                    medicamentInformation.added = new java.sql.Date(addedDate.getTime());
                    Date soldDate = simpleDateFormat.parse(soldField.getText());
                    medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
                    medicamentInformation.update();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                medicamentInformation = new MedicamentInformation();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date expirationDate = simpleDateFormat.parse(expirationField.getText());
                    medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
                    Date addedDate = simpleDateFormat.parse(addedField.getText());
                    medicamentInformation.added = new java.sql.Date(addedDate.getTime());
                    Date soldDate = simpleDateFormat.parse(soldField.getText());
                    medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
                    medicamentInformation.insert();
                    toUpdate.medicamentInformationID = medicamentInformation.medicamentInformationID;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Price price = Price.findById(toUpdate.priceID);
            if (price != null){
                price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
                price.sellingPrice = new BigDecimal(sellingPriceField.getText());
                price.update();
            } else{
                price = new Price();
                price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
                price.sellingPrice = new BigDecimal(sellingPriceField.getText());
                price.insert();
                toUpdate.priceID = price.priceID;
            }

            SaleCategory saleCategory = SaleCategory.findById(toUpdate.saleCategoryId);
            if (saleCategory != null){
                saleCategory.title = saleCategoriesField.getText();
                saleCategory.update();
            }else {
                saleCategory = new SaleCategory();
                saleCategory.title  =saleCategoriesField.getText();
                saleCategory.insert();
                toUpdate.saleCategoryId = saleCategory.saleCategoryID;
            }

            InMedicamentCategory inMedicamentCategory = InMedicamentCategory.findByMedicamentId(toUpdate.medicamentID);
            if (inMedicamentCategory != null){
                MedicamentCategory medicamentCategory = MedicamentCategory.findById(inMedicamentCategory.medicament_category_id);
                if (medicamentCategory != null){
                    medicamentCategory.title = medicamentCategoriesField.getText();
                    medicamentCategory.update();
                } else {
                    medicamentCategory = new MedicamentCategory();
                    medicamentCategory.title = medicamentCategoriesField.getText();
                    medicamentCategory.insert();
                    inMedicamentCategory.medicament_category_id = medicamentCategory.medicamentCategoryID;
                    inMedicamentCategory.update();
                }
            } else {
                inMedicamentCategory = new InMedicamentCategory();
                inMedicamentCategory.medicament_id = toUpdate.medicamentID;
                MedicamentCategory medicamentCategory = MedicamentCategory.findByTitle(medicamentCategoriesField.getText());
                if (medicamentCategory != null){
                    inMedicamentCategory.medicament_category_id = medicamentCategory.medicamentCategoryID;
                    inMedicamentCategory.update();
                } else {
                    medicamentCategory = new MedicamentCategory();
                    medicamentCategory.title = medicamentCategoriesField.getText();
                    inMedicamentCategory.medicament_category_id = medicamentCategory.medicamentCategoryID;
                    inMedicamentCategory.update();
                }

            }

            State state = State.findById(toUpdate.stateID);
            if (state != null){
                state.title = stateField.getText();
                state.update();
            } else {
                state = new State();
                state.title = stateField.getText();
                state.insert();
                toUpdate.stateID  =state.stateID;
            }

            toUpdate.update();
        }

        updated = toUpdate;
        titleField.getScene().getWindow().hide();
    }

    private boolean verifyInputs(){
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
