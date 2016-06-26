package guiTS;

import appCore.transactionScript.rowGateways.*;
import appCore.transactionScript.transactionScripts.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowControllerTS implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private Label codeLabel;

    @FXML
    private Label batchLabel;

    @FXML
    private Label addedLabel;

    @FXML
    private Label expirationLabel;

    @FXML
    private Label soldLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label saleCategoryLabel;

    @FXML
    private Label medicamentCategoryLabel;

    @FXML
    private Label sellingPriceLabel;

    @FXML
    private Label buyoutPriceLabel;


    @FXML
    private ListView<Medicament> medicamentsListView;
    private List<Medicament> medicamentsList;
    private ObservableList<Medicament> medicaments;


    public void recipesButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipesTS.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            RecipesControllerTS recipesController = loader.<RecipesControllerTS>getController();

            stage.setTitle("Recepty");
            stage.setScene(new Scene(root, 600, 400));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleStatsButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StatsTS.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            StatsControllerTS controller = loader.<StatsControllerTS>getController();


            // pocet vsetkych liekov
            int numberOfAllMedicaments = Stats.getNumberOfAllMedicaments();
            controller.numberOfAllMedicamentsLabel.setText(String.valueOf(numberOfAllMedicaments));

            //pocet liekov na sklade
            int numberOfMedicamentsInStore = Stats.getNumberOfMedicamentsInStore();
            controller.numberOfMedicamentsInStoreLabel.setText(String.valueOf(numberOfMedicamentsInStore));

            // pocet expirovanych liekov
            int numberOfExpiredMedicaments = Stats.getNumberOfExpiredMedicaments();
            controller.numberOfExpiredMedicamentsLabel.setText(String.valueOf(numberOfExpiredMedicaments));

            //pocet liekov v labaku
            int numberOfMedicamentsInLab = Stats.getNumberOfMedicamentsInLab();
            controller.numberOfMedicamentsInLab.setText(String.valueOf(numberOfMedicamentsInLab));

            // pocet vsetkych receptov
            int numberOfAllRecipes = Stats.getNumberOfAllRecipes();
            controller.numberOfAllRecipes.setText(String.valueOf(numberOfAllRecipes));

            //pocet retaxovanych
            int numberOfAllRetaxedRecipes = Stats.getNumberOfRetaxedrecipes();
            controller.numberOfRetaxedRecipes.setText(String.valueOf(numberOfAllRetaxedRecipes));

            //pocet neretaxovanych
            int numberOfAllUretaxedRecipes = Stats.getNumberOfUnRetaxedRecipes();
            controller.numberOfNonRetaxedRecipes.setText(String.valueOf(numberOfAllUretaxedRecipes));


            stage.setTitle("Stats");
            stage.setScene(new Scene(root, 600, 400));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked new window with text fields, if new medicamentCategories was inserted, adds it to listView
     */
    public void handleAddButtonAction(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddWindowsTS.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            AddWindowControllerTS controller = loader.<AddWindowControllerTS>getController();

            stage.setTitle("Add Window");
            stage.setScene(new Scene(root, 355, 414));
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
    public void handleDeleteButtonAction(ActionEvent event) {
        ObservableList<Medicament> toDelete = medicamentsListView.getSelectionModel().getSelectedItems();

        if (!toDelete.isEmpty()) {
            for (Medicament item : toDelete) {
                medicaments.remove(item);

                Price price = Price.findById(item.priceID);
                MedicamentInformation medicamentInformation = MedicamentInformation.findByID(item.medicamentInformationID);
                InMedicamentCategory inMedicamentCategory = InMedicamentCategory.findByMedicamentId(item.medicamentID);
                if (inMedicamentCategory != null)
                    inMedicamentCategory.delete();

                item.delete();
                if (price != null)
                    price.delete();
                if (medicamentInformation != null)
                    medicamentInformation.delete();
            }
        }
    }

    public void handleUpdateButton(ActionEvent event) {
        try {
            Medicament toUpdate = medicamentsListView.getSelectionModel().getSelectedItem();

            if (toUpdate != null) {
                MedicamentInformation medicamentInformation = MedicamentInformation.findByID(toUpdate.medicamentID);
                Price price = Price.findById(toUpdate.priceID);
                SaleCategory saleCategory = SaleCategory.findById(toUpdate.saleCategoryId);
                InMedicamentCategory inMedicamentCategory = InMedicamentCategory.findByMedicamentId(toUpdate.medicamentID);
                MedicamentCategory medicamentCategory = null;
                State state = State.findById(toUpdate.stateID);
                if (inMedicamentCategory != null) {
                    medicamentCategory = MedicamentCategory.findById(inMedicamentCategory.medicament_category_id);
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateTS.fxml"));
                Parent root = (Parent) loader.load();

                Stage stage = new Stage();
                UpdateControllerTS controller = loader.<UpdateControllerTS>getController();
                controller.toUpdate = toUpdate;

                controller.titleField.setText(toUpdate.title);
                controller.codeField.setText(toUpdate.code);
                controller.batchField.setText(toUpdate.batch);

                controller.medicamentID = toUpdate.medicamentID;

                if (saleCategory != null) {
                    controller.saleCategoriesField.setText(saleCategory.title);
                    controller.saleCategoryID = saleCategory.saleCategoryID;
                }

                if (medicamentCategory != null) {
                    controller.medicamentCategoriesField.setText(medicamentCategory.title);
                    controller.medicamentCategoryID = medicamentCategory.medicamentCategoryID;
                }

                if (state != null) {
                    controller.stateField.setText(state.title);
                    controller.stateID = state.stateID;
                }

                if (price != null) {
                    if (price.sellingPrice != null)
                        controller.sellingPriceField.setText(price.sellingPrice.toString());
                    if (price.buyoutPrice != null)
                        controller.buyoutPriceField.setText(price.buyoutPrice.toString());
                    controller.priceID = price.priceID;
                }

                if (medicamentInformation != null) {
                    controller.addedField.setText(medicamentInformation.added.toString());
                    controller.soldField.setText(medicamentInformation.sold.toString());
                    controller.expirationField.setText(medicamentInformation.expiration.toString());
                    controller.medicamentInformationID = medicamentInformation.medicamentInformationID;
                }

                stage.setTitle("Update Window");
                stage.setScene(new Scene(root, 355, 414));
                stage.showAndWait();

                if (controller.updated != null) {
                    System.out.println("added");
                    medicaments.remove(toUpdate);
                    medicaments.add(controller.updated);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize method, it is called at beggining of the application, it sets up list view items
     * and also define listener for values changes in observable list of medicaments.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicamentsList = Medicament.getAllMedicaments();
        medicaments = FXCollections.observableArrayList(medicamentsList);

        medicamentsListView.setItems(medicaments);
        if (!medicaments.isEmpty()) {
            medicamentsListView.getSelectionModel().select(0);
            Medicament medicament = medicamentsListView.getSelectionModel().getSelectedItem();
            updateInformationLabels(medicament);
        }


        medicamentsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);

            if (newValue != null)
                updateInformationLabels(newValue);

        });
    }

    /**
     * Updates infromation labels with information about given medicamentCategories
     */
    private void updateInformationLabels(Medicament medicament) {

        titleLabel.setText("");
        codeLabel.setText("");
        batchLabel.setText("");
        addedLabel.setText("");
        expirationLabel.setText("");
        soldLabel.setText("");
        stateLabel.setText("");
        saleCategoryLabel.setText("");
        medicamentCategoryLabel.setText("");
        sellingPriceLabel.setText("");
        buyoutPriceLabel.setText("");


        Price price = Price.findById(medicament.priceID);

        MedicamentInformation medicamentInformation = MedicamentInformation.findByID(medicament.medicamentInformationID);

        State state = State.findById(medicament.stateID);

        InMedicamentCategory inMedicamentCategory = InMedicamentCategory.findByMedicamentId(medicament.medicamentID);

        SaleCategory saleCategory = SaleCategory.findById(medicament.saleCategoryId);

        MedicamentCategory medicamentCategory = null;
        if (inMedicamentCategory != null)
            medicamentCategory = MedicamentCategory.findById(inMedicamentCategory.medicament_category_id);


        titleLabel.setText(medicament.title);
        codeLabel.setText(medicament.code);
        batchLabel.setText(medicament.batch);

        if (price != null) {
            sellingPriceLabel.setText(String.valueOf(price.sellingPrice));
            buyoutPriceLabel.setText(String.valueOf(price.buyoutPrice));
        }

        if (medicamentInformation != null) {
            addedLabel.setText(String.valueOf(medicamentInformation.added));
            expirationLabel.setText(String.valueOf(medicamentInformation.expiration));
            soldLabel.setText(String.valueOf(medicamentInformation.sold));
        }

        if (state != null) {
            stateLabel.setText(state.title);
        }

        if (saleCategory != null)
            saleCategoryLabel.setText(saleCategory.title);

        if (medicamentCategory != null)
            medicamentCategoryLabel.setText(medicamentCategory.title);

    }
}
