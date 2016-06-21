package guiDM;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable{

    @FXML
    public Label numberOfAllMedicamentsLabel;

    @FXML
    public Label numberOfMedicamentsInStoreLabel;

    @FXML
    public Label numberOfSoldMedicamentsLabel;

    @FXML
    public Label numberOfExpiredMedicamentsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
