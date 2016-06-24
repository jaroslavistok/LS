package guiDM;

import appCore.dataMapper.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddWindowControllerDM implements Initializable {
    @FXML
    TextField titleField;

    @FXML
    TextField batchField;

    @FXML
    TextField soldField;

    @FXML
    TextField stateField;

    @FXML
    TextField codeField;

    @FXML
    TextField addedField;

    @FXML
    TextField expirationField;

    @FXML
    TextField saleCategoriesField;

    @FXML
    TextField medicamentCategoriesField;

    @FXML
    TextField sellingPriceField;

    @FXML
    TextField buyoutPriceField;

    appCore.dataMapper.Medicament insertedMedicament;

    public appCore.dataMapper.Medicament getMedicament() {
        return insertedMedicament;
    }

    //private Medicament medicamentCategories;

    /**
     * veryfi and saves given information to the database
     */
    public void handleOkButtonAction(ActionEvent event) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();


        Price price = new Price();
        price.seelingPrice = new BigDecimal(sellingPriceField.getText());
        price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
        entityManager.persist(price);

        MedicamentInformation medicamentInformation = new MedicamentInformation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date expirationDate = dateFormat.parse(expirationField.getText());
            medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
            Date addedDate = dateFormat.parse(addedField.getText());
            medicamentInformation.added = new java.sql.Date(addedDate.getTime());
            Date soldDate = dateFormat.parse(soldField.getText());
            medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.persist(medicamentInformation);

        Query query = entityManager.createNamedQuery("Find category by title", SaleCategory.class);
        query.setParameter("title", saleCategoriesField.getText());
        List<SaleCategory> saleCategoryList = query.getResultList();

        SaleCategory saleCategory = new SaleCategory();
        if (saleCategoryList.size() == 0) {
            saleCategory.title = saleCategoriesField.getText();
            entityManager.persist(saleCategory);
        } else {
            saleCategory = saleCategoryList.get(0);
        }

        query = entityManager.createNamedQuery("find by name", SaleCategory.class);
        query.setParameter("title", medicamentCategoriesField.getText());
        List<MedicamentCategory> medicamentCategories = query.getResultList();

        MedicamentCategory medicamentCategory = new MedicamentCategory();
        if (medicamentCategories.size() == 0) {
            medicamentCategory.title = medicamentCategoriesField.getText();
            entityManager.persist(medicamentCategory);
        } else {
            medicamentCategory = medicamentCategories.get(0);
        }

        Medicament medicament = new Medicament();
        medicament.price = price;
        medicament.saleCategory =saleCategory;
        medicament.medicamentCategories = medicamentCategories;
        medicament.medicamentInformation = medicamentInformation;
        medicament.title = titleField.getText();
        medicament.code = codeField.getText();
        medicament.batch = batchField.getText();
        entityManager.persist(medicament);

        entityManager.getTransaction().commit();
        System.out.println("Added");
        insertedMedicament = medicament;
        titleField.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
