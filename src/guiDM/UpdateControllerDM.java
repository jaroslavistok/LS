package guiDM;

import appCore.dataMapper.MedicamentInformation;
import appCore.dataMapper.Price;
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

public class UpdateControllerDM implements Initializable {

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

    public void handleUpdateButtonAction(ActionEvent event){

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

        Query query = entityManager.createNamedQuery("Find category by title", appCore.dataMapper.SaleCategory.class);
        query.setParameter("title", saleCategoriesField.getText());
        List<appCore.dataMapper.SaleCategory> saleCategoryList = query.getResultList();

        appCore.dataMapper.SaleCategory saleCategory = new appCore.dataMapper.SaleCategory();
        if (saleCategoryList.size() == 0) {
            saleCategory.title = saleCategoriesField.getText();
            entityManager.persist(saleCategory);
        } else {
            saleCategory = saleCategoryList.get(0);
        }

        query = entityManager.createNamedQuery("find by name", appCore.dataMapper.SaleCategory.class);
        query.setParameter("title", medicamentCategoriesField.getText());
        List<appCore.dataMapper.MedicamentCategory> medicamentCategories = query.getResultList();

        appCore.dataMapper.MedicamentCategory medicamentCategory = new appCore.dataMapper.MedicamentCategory();
        if (medicamentCategories.size() == 0) {
            medicamentCategory.title = medicamentCategoriesField.getText();
            entityManager.persist(medicamentCategory);
        } else {
            medicamentCategory = medicamentCategories.get(0);
        }

        appCore.dataMapper.Medicament medicament = new appCore.dataMapper.Medicament();
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



        titleField.getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
