package guiDM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

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


    public void handleUpdateButtonAction(ActionEvent event){

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
