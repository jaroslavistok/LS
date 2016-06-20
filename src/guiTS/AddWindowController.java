package guiTS;

import appCore.transactionScript.rowGateways.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        // saves Price, every medicament has its own price
        String priceField = cenyField.getText();
        String[] prices = priceField.split(",");
        Price price = new Price();
        price.buyoutPrice = new BigDecimal(prices[0]);
        price.sellingPrice = new BigDecimal(prices[1]);
        price.insert();

        // saves medicament information, every medicament has its own information
        MedicamentInformation medicamentInformation = new MedicamentInformation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date parsed = dateFormat.parse(stavField.getText());
            medicamentInformation.expiration = new java.sql.Date(parsed.getTime());
            // to do other fields
            medicamentInformation.insert();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // inserts new state only if this state don't exists already
        State state = new State();
        state.title = stavField.getText();
        state.insert();

        // inserts new category only if desired category doesn't exists
        MedicamentCategory medicamentCategory = new MedicamentCategory();
        medicamentCategory.title = "title";
        medicamentCategory.insert();

        // update binding table
        InMedicamentCategory inMedicamentCategory = new InMedicamentCategory();
        inMedicamentCategory.medicament_category_id = medicamentCategory.lastInsertedID;
        inMedicamentCategory.medicament_id = medicament.medicamentID;
        inMedicamentCategory.insert();

        // insetts new sale category only if this category doesnt exists
        SaleCategory saleCategory = new SaleCategory();
        saleCategory.title = "sale category1";
        saleCategory.insert();

        // finaly inserts new medicament with all foreign keys and so on
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
