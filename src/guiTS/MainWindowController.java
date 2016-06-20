package guiTS;
import appCore.transactionScript.rowGateways.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Label nazovLabel;

    @FXML
    private Label kodLabel;

    @FXML
    private Label davkaLabel;

    @FXML
    private Label pridaneNaSkladLabel;

    @FXML
    private Label expiraciaLabel;

    @FXML
    private Label predaneLabel;

    @FXML
    private Label stavLabel;

    @FXML
    private Label predajnaKategoriaLabel;

    @FXML
    private Label kategoriaLiekuLabel;

    @FXML
    private Label cenyLabel;

    @FXML
    private ListView<Medicament> liekyListView;
    private List<Medicament> medicamentsList;
    private ObservableList<Medicament> medicaments;


    /**
     * Invoked new window with text fields, if new medicament was inserted, adds it to listView
     */
    public void handleAddButtonAction(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddWindows.fxml"));
            Parent root = (Parent)loader.load();

            Stage stage = new Stage();
            AddWindowController controller = loader.<AddWindowController>getController();

            stage.setTitle("Add Window");
            stage.setScene(new Scene(root, 400, 600));
            stage.showAndWait();

            if (controller.getMedicament() != null)
                medicaments.add(controller.getMedicament());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * handler, deletes selected item form database and from listView
     */
    public void handleDeleteButtonAction(ActionEvent event){
        ObservableList<Medicament> toDelete = liekyListView.getSelectionModel().getSelectedItems();
        for (Medicament item : toDelete){
            medicaments.remove(item);
            item.delete();
        }
    }

    public void handleUpdateButton(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Update.fxml"));
            Parent root = (Parent)loader.load();

            Stage stage = new Stage();
            UpdateController controller = loader.<UpdateController>getController();
            controller.cenyField.setText("Ceny");

            stage.setTitle("Update Window");
            stage.setScene(new Scene(root, 400, 600));
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize method, it is called at beggining of the application, it sets up list view items
     * and also define listener for values changes in observable list of medicaments.
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicamentsList = Medicament.getAllMedicaments();
        medicaments = FXCollections.observableArrayList(medicamentsList);

        liekyListView.setItems(medicaments);
        if (!medicaments.isEmpty()){
            liekyListView.getSelectionModel().select(0);
        }


        liekyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);
            // pomocou metod v medicament povytahujem potrebne udaje a aktualizujem labely
            // treba srpavit transaction script
            updateInformationLabels(newValue);


        });
    }

    /**
     * Updates infromation labels with information about given medicament
     */
    private void updateInformationLabels(Medicament medicament){

        // gets information from db
        Price price = Price.findById(medicament.priceID);
        MedicamentInformation medicamentInformation = MedicamentInformation.findByID(medicament.medicamentInformationID);
        State state = State.findById(medicament.stateID);
        InMedicamentCategory inMedicamentCategory = InMedicamentCategory.findByMedicamentId(medicament.medicamentID);
        MedicamentCategory medicamentCategory = MedicamentCategory.findById(inMedicamentCategory.medicament_category_id);
        SaleCategory saleCategory = SaleCategory.findById(medicament.saleCategoryId);


        nazovLabel.setText(medicament.title);
        kodLabel.setText(medicament.code);
        davkaLabel.setText(medicament.batch);
        pridaneNaSkladLabel.setText(String.valueOf(medicamentInformation.added));
        expiraciaLabel.setText(String.valueOf(medicamentInformation.expiration));
        predaneLabel.setText(String.valueOf(medicamentInformation.sold));
        stavLabel.setText(state.title);
        predajnaKategoriaLabel.setText(saleCategory.title);
        kategoriaLiekuLabel.setText(saleCategory.title);

    }
}
