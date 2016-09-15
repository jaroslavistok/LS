package guiDM;

import appCore.dataMapper.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @FXML
    TextArea errorsLog;

    public Medicament medicamentToUpdate;
    public Medicament updated;

    public void handleUpdateButtonAction(ActionEvent event) {
        if (!verifyInputs())
            return;
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Medicament m = entityManager.find(Medicament.class, medicamentToUpdate.medicamentId);
        m.title = titleField.getText();
        m.code = codeField.getText();
        m.batch = batchField.getText();
        entityManager.getTransaction().commit();


        Query query = entityManager.createNamedQuery("find by name", MedicamentCategory.class);
        query.setParameter("title", medicamentCategoriesField.getText());
        MedicamentCategory medicamentCategory = null;
        try {
            entityManager.getTransaction().begin();
            medicamentCategory = (MedicamentCategory) query.getSingleResult();
            medicamentCategory.title = medicamentCategoriesField.getText();
            if (!m.medicamentCategories.contains(medicamentCategory))
                m.medicamentCategories.add(medicamentCategory);
            entityManager.getTransaction().commit();

        } catch (NoResultException e) {
            medicamentCategory = new MedicamentCategory();
            entityManager.persist(medicamentCategory);
            medicamentCategory.title = medicamentCategoriesField.getText();
            m.medicamentCategories.add(medicamentCategory);
            entityManager.getTransaction().commit();
        }


        //sale category
        if (m.saleCategory != null) {
            entityManager.getTransaction().begin();
            m.saleCategory = entityManager.find(SaleCategory.class, m.saleCategory.saleCategoryID);
            m.saleCategory.title = saleCategoriesField.getText();
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().begin();
            m.saleCategory = new SaleCategory();
            entityManager.persist(m.saleCategory);
            m.saleCategory.title = saleCategoriesField.getText();
            entityManager.getTransaction().commit();
        }

        //price
        if (m.price != null) {
            entityManager.getTransaction().begin();
            m.price = entityManager.find(Price.class, m.price.priceId);
            m.price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
            m.price.seelingPrice = new BigDecimal(sellingPriceField.getText());
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().begin();
            m.price = new Price();
            entityManager.persist(m.price);
            m.price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
            m.price.seelingPrice = new BigDecimal(sellingPriceField.getText());
            entityManager.getTransaction().commit();
        }


        // information
        if (m.medicamentInformation != null) {
            entityManager.getTransaction().begin();
            m.medicamentInformation = entityManager.find(MedicamentInformation.class, m.medicamentInformation.medicamentInformationID);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date expirationDate = dateFormat.parse(expirationField.getText());
                m.medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
                Date addedDate = dateFormat.parse(addedField.getText());
                m.medicamentInformation.added = new java.sql.Date(addedDate.getTime());
                Date soldDate = dateFormat.parse(soldField.getText());
                m.medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().begin();
            m.medicamentInformation = new MedicamentInformation();
            entityManager.persist(m.medicamentInformation);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date expirationDate = dateFormat.parse(expirationField.getText());
                m.medicamentInformation.expiration = new java.sql.Date(expirationDate.getTime());
                Date addedDate = dateFormat.parse(addedField.getText());
                m.medicamentInformation.added = new java.sql.Date(addedDate.getTime());
                Date soldDate = dateFormat.parse(soldField.getText());
                m.medicamentInformation.sold = new java.sql.Date(soldDate.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            entityManager.getTransaction().commit();
        }

        //state
        if (m.state != null) {
            entityManager.getTransaction().begin();
            m.state = entityManager.find(State.class, m.state.stateID);
            m.state.title = titleField.getText();
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().begin();
            m.state = new State();
            entityManager.persist(m.state);
            m.state.title = titleField.getText();
            entityManager.getTransaction().commit();
        }

        System.out.println("Updated");
        updated = m;

        titleField.getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean verifyInputs() {
        errorsLog.setText("");
        boolean valid = true;

        if (titleField.getText().isEmpty()) {
            errorsLog.appendText("Nazov nesmie byt prazdny\n");
            valid = false;
        }

        if (codeField.getText().isEmpty()) {
            errorsLog.appendText("Kazdy liek musi obsahovat kod\n");
            valid = false;
        }

        if (batchField.getText().isEmpty()) {
            errorsLog.appendText("Kazdy liek musi byt v nejakej davke\n");
            valid = false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        try {
            dateFormat.parse(expirationField.getText());
            dateFormat.parse(addedField.getText());
            dateFormat.parse(soldField.getText());
        } catch (ParseException e) {
            errorsLog.appendText("Zle zadany format datumu\n");
            valid = false;
        }

        if (saleCategoriesField.getText().isEmpty()) {
            errorsLog.appendText("predajna kateogria nesmie byt prazdna\n");
            valid = false;

        }

        if (medicamentCategoriesField.getText().isEmpty()) {
            errorsLog.appendText("kategoria lieku nesmie byt prazdna\n");
            valid = false;

        }

        if (stateField.getText().isEmpty()) {
            errorsLog.appendText("liek musi mat stav\n");
            valid = false;

        }

        if (buyoutPriceField.getText().isEmpty()) {
            errorsLog.appendText("nakupna cena musi byt vyplnena\n");
            valid = false;

        }

        if (sellingPriceField.getText().isEmpty()) {
            errorsLog.appendText("predajna cena musi byt vyplnena\n");
            valid = false;

        }

        try {
            BigDecimal bigDecimal = new BigDecimal(buyoutPriceField.getText());
            bigDecimal = new BigDecimal(sellingPriceField.getText());
        } catch (NumberFormatException e) {
            errorsLog.appendText("Zle zadana suma\n");
            valid = false;

        }

        return valid;
    }

}
