package guiDM;

import appCore.dataMapper.Medicament;
import appCore.dataMapper.MedicamentCategory;
import appCore.dataMapper.MedicamentInformation;
import appCore.dataMapper.Price;
import appCore.domainModel.MedicamentSelects;
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

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowControllerDM implements Initializable {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipesDM.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            RecipesControllerDM recipesController = loader.<RecipesControllerDM>getController();

            stage.setTitle("Recepty");
            stage.setScene(new Scene(root, 800, 500));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleStatsButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StatsDM.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            StatsControllerDM controller = loader.<StatsControllerDM>getController();

            int numberOfAllMedicaments = 0;
            controller.numberOfAllMedicamentsLabel.setText(String.valueOf(numberOfAllMedicaments));

            stage.setTitle("Stats");
            stage.setScene(new Scene(root, 800, 500));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked new window with text fields, if new medicamentCategories was inserted, adds it to listView
     */
    public void handleAddButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddWindowsDM.fxml"));
            Parent root = (Parent) loader.load();

            Stage stage = new Stage();
            AddWindowControllerDM controller = loader.<AddWindowControllerDM>getController();

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
    public void handleDeleteButtonAction(ActionEvent event) {
        ObservableList<Medicament> toDelete = medicamentsListView.getSelectionModel().getSelectedItems();
        for (Medicament item : toDelete) {
            EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
            entityManager.getTransaction().begin();

            if (item.price != null) {
                Price price = entityManager.find(Price.class, item.price.priceId);
                if (price != null)
                    entityManager.remove(price);
            }
            if (item.medicamentInformation != null) {
                MedicamentInformation medicamentInformation = entityManager.find(MedicamentInformation.class, item.medicamentInformation.medicamentInformationID);
                if (medicamentInformation != null){
                    entityManager.remove(medicamentInformation);
                }
            }

            Medicament medicament = entityManager.find(Medicament.class, item.medicamentId);
            entityManager.remove(medicament);

            entityManager.getTransaction().commit();
            medicaments.remove(item);
        }




    }

    public void handleUpdateButton(ActionEvent event) {
        try {
            ObservableList<Medicament> toUpdate = medicamentsListView.getSelectionModel().getSelectedItems();

            if (!toUpdate.isEmpty()) {
                Medicament medicament = toUpdate.get(0);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDM.fxml"));
                Parent root = (Parent) loader.load();

                Stage stage = new Stage();
                UpdateControllerDM controller = loader.<UpdateControllerDM>getController();

                controller.titleField.setText("");
                controller.codeField.setText("");
                controller.batchField.setText("");

                stage.setTitle("Update Window");
                stage.setScene(new Scene(root, 400, 600));
                stage.showAndWait();

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
        MedicamentSelects medicamentSelects = new MedicamentSelects();
        medicamentsList = medicamentSelects.getAllMedicaments();

        medicaments = FXCollections.observableArrayList(medicamentsList);

        medicamentsListView.setItems(medicaments);
        if (!medicaments.isEmpty()) {
            medicamentsListView.getSelectionModel().select(0);
        }


        medicamentsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);
            // pomocou metod v medicamentCategories povytahujem potrebne udaje a aktualizujem labely
            // treba srpavit transaction script
            updateInformationLabels(newValue);


        });
    }

    /**
     * Updates infromation labels with information about given medicamentCategories
     */
    private void updateInformationLabels(Medicament medicament) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();
        titleLabel.setText(medicament.title);
        codeLabel.setText(medicament.code);
        batchLabel.setText(medicament.batch);


        if (medicament.medicamentInformation != null) {
            expirationLabel.setText(medicament.medicamentInformation.expiration.toString());
            addedLabel.setText(medicament.medicamentInformation.added.toString());
            soldLabel.setText(medicament.medicamentInformation.sold.toString());
        }


        if (medicament.state != null) {
            stateLabel.setText(medicament.state.title);
        }

        if (medicament.saleCategory != null) {
            saleCategoryLabel.setText(medicament.saleCategory.title);
        }

        if (medicament.medicamentCategories != null) {
            for (MedicamentCategory medicamentCategory : medicament.medicamentCategories)
                medicamentCategoryLabel.setText(medicamentCategory.title);
        }

        if (medicament.price != null) {
            if (medicament.price.buyoutPrice != null)
                buyoutPriceLabel.setText(medicament.price.buyoutPrice.toString());
            if (medicament.price.seelingPrice != null)
                sellingPriceLabel.setText(medicament.price.seelingPrice.toString());
        }

        entityManager.getTransaction().commit();
        // gets information from db via domain model
    }
}
