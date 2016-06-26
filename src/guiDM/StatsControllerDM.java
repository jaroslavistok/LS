package guiDM;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsControllerDM implements Initializable{

    @FXML
    public Label numberOfAllMedicamentsLabel;

    @FXML
    public Label numberOfMedicamentsInStoreLabel;

    @FXML
    public Label numberOfSoldMedicamentsLabel;

    @FXML
    public Label numberOfExpiredMedicamentsLabel;

    @FXML
    public Label numberOfNonRetaxedRecipes;

    @FXML
    public Label numberOfMedicamentsInLab;

    @FXML
    public Label numberOfAllRecipes;

    @FXML
    public Label numberOfRetaxedRecipes;

    @FXML
    public Label valueOfMedicaments;

    @FXML
    public Label incomeLastMonth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
