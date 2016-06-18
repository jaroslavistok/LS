package gui;

import app.transactionScript.rowGateways.Medicament;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    public void handleAddButtonAction(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddWindows.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Window");
            stage.setScene(new Scene(root, 400, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Medicament> medicamentsList = Medicament.getAllMedicaments();
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList(medicamentsList);

        liekyListView.setItems(medicaments);
        if (!medicaments.isEmpty()){
            liekyListView.getSelectionModel().select(0);
        }


        liekyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);
            // pomocou metod v medicament povytahujem potrebne udaje a aktualizujem labely
        });
    }
}
