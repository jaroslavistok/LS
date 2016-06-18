package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
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

    public void handleOkButtonAction(ActionEvent event){
        // ulozi data z textfieldov do db
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
