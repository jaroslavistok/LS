package guiTS;

import appCore.transactionScript.rowGateways.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    public void handleUpdateButtonAction(ActionEvent event){
// saves Price information, every medicament has its own price
        Price price = new Price();
        price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
        price.sellingPrice = new BigDecimal(sellingPriceField.getText());
        price.update();

        // saves medicament information, every medicament has its own information
        MedicamentInformation medicamentInformation = new MedicamentInformation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-mm");
        try {
            Date expirationDate = dateFormat.parse(expirationField.getText());
            medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
            Date soldDate = dateFormat.parse(soldField.getText());
            medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
            Date buyoutDate = dateFormat.parse(addedField.getText());
            medicamentInformation.added = new java.sql.Date(buyoutDate.getTime());
            medicamentInformation.update();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        // inserts new state only if this state don't exists already
        State state = State.findByTitle(stateField.getText());
        if (state == null) {
            state = new State();
            state.title = stateField.getText();
            state.update();
        }


        // inserts new category only if desired category doesn't exists
        MedicamentCategory medicamentCategory = MedicamentCategory.findByTitle(medicamentCategoriesField.getText());
        if (medicamentCategory == null){
            medicamentCategory = new MedicamentCategory();
            medicamentCategory.title = medicamentCategoriesField.getText();
            medicamentCategory.update();
        }




        // inserts new sale category only if this category doesnt exists
        SaleCategory saleCategory = SaleCategory.findByName(saleCategoriesField.getText());
        if (saleCategory == null) {
            saleCategory = new SaleCategory();
            saleCategory.title = saleCategoriesField.getText();
            saleCategory.update();
        }


        // finaly inserts new medicament with all foreign keys and so on

        Medicament medicament = new Medicament();
        medicament.title = titleField.getText();
        medicament.batch = batchField.getText();
        medicament.code = codeField.getText();
        medicament.medicamentInformationID = medicamentInformation.medicamentInformationID;
        medicament.priceID = price.priceID;
        medicament.saleCategoryId = saleCategory.saleCategoryID;
        medicament.stateID = state.stateID;
        medicament.update();

        // at the end update binding table
        InMedicamentCategory inMedicamentCategory = new InMedicamentCategory();
        inMedicamentCategory.medicament_category_id = medicamentCategory.medicamentCategoryID;
        inMedicamentCategory.medicament_id = medicament.medicamentID;
        inMedicamentCategory.update();

        System.out.println(medicamentCategory.medicamentCategoryID);
        System.out.println(medicament.medicamentID);

        titleField.getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
