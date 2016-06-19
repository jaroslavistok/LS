package gui;

import app.transactionScript.rowGateways.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddWindowController implements Initializable {
    @FXML
    Button button;

    @FXML
    TextField nazovField;

    @FXML
    TextField davkaField;

    @FXML
    TextField predaneField;

    @FXML
    TextField stavField;

    @FXML
    TextField kodField;

    @FXML
    TextField pridaneField;

    @FXML
    TextField predajnaKategoriaField;

    @FXML
    TextField kategoriaLiekuField;

    @FXML
    TextField cenyField;

    public Medicament getMedicament() {
        return medicament;
    }

    private Medicament medicament;

    /**
     * veryfi and saves given information to the database
     */
    public void handleOkButtonAction(ActionEvent event){
        Price price = new Price();
        price.patient = BigDecimal.valueOf(200);
        price.insert();

        MedicamentInformation medicamentInformation = new MedicamentInformation();
        medicamentInformation.expiration = new Date(4342424242L);
        medicamentInformation.sold = new Date(4342424242L);
        medicamentInformation.expiration = new Date(4342424242L);
        medicamentInformation.insert();

        State state = new State();
        state.title = "State1";
        state.insert();

        MedicamentCategory medicamentCategory = new MedicamentCategory();
        medicamentCategory.title = "title";
        medicamentCategory.insert();

        InMedicamentCategory inMedicamentCategory = new InMedicamentCategory();
        inMedicamentCategory.medicament_category_id = medicamentCategory.lastInsertedID;
        inMedicamentCategory.medicament_id = medicament.medicamentID;
        inMedicamentCategory.insert();

        SaleCategory saleCategory = new SaleCategory();
        saleCategory.title = "sale category1";
        saleCategory.insert();

        medicament = new Medicament();
        medicament.title = nazovField.getText();
        medicament.batch = davkaField.getText();
        medicament.code = kodField.getText();
        medicament.insertWithoutForeignKeys();
        cenyField.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
