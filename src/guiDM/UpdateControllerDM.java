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

    public void handleUpdateButtonAction(ActionEvent event){
        if (!verifyInputs())
            return;
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();
        if (medicamentToUpdate.price != null) {
            Price price = entityManager.find(Price.class, medicamentToUpdate.price.priceId);
            if (price != null) {
                price.seelingPrice = new BigDecimal(sellingPriceField.getText());
                price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
            } else {
                price = new Price();
                entityManager.persist(price);
                price.seelingPrice = new BigDecimal(sellingPriceField.getText());
                price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
            }
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        if (medicamentToUpdate.medicamentInformation != null) {
            MedicamentInformation medicamentInformation = entityManager.find(MedicamentInformation.class, medicamentToUpdate.medicamentInformation.medicamentInformationID);
            if (medicamentInformation != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
            }
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        SaleCategory saleCategory = entityManager.find(SaleCategory.class, medicamentToUpdate.saleCategory.saleCategoryID);
        if (saleCategory != null) {
            saleCategory.title = saleCategoriesField.getText();
        } else {
            saleCategory = new SaleCategory();
            saleCategory.title = saleCategoriesField.getText();
            entityManager.persist(saleCategory);
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("find by name");
        query.setParameter("title", medicamentCategoriesField.getText());
        try {
            MedicamentCategory medicamentCategory = (MedicamentCategory) query.getSingleResult();
            medicamentCategory.title = medicamentCategoriesField.getText();
        } catch (NoResultException e) {
            MedicamentCategory medicamentCategory = new MedicamentCategory();
            entityManager.persist(medicamentCategory);
            medicamentCategory.title = medicamentCategoriesField.getText();
        }
        entityManager.getTransaction().commit();

        if (medicamentToUpdate.price != null) {
            entityManager.getTransaction().begin();
            Price price = entityManager.find(Price.class, medicamentToUpdate.price.priceId);
            price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
            price.seelingPrice = new BigDecimal(sellingPriceField.getText());
            entityManager.getTransaction().commit();
        }
        entityManager.getTransaction().begin();
        Medicament m = entityManager.find(Medicament.class, medicamentToUpdate.medicamentId);
        m.title = titleField.getText();
        m.code = codeField.getText();
        m.batch = batchField.getText();
        entityManager.getTransaction().commit();
        entityManager.close();


        titleField.getScene().getWindow().hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean verifyInputs(){
        errorsLog.setText("");
        boolean valid = true;

        if (titleField.getText().isEmpty()){
            errorsLog.appendText("Nazov nesmie byt prazdny\n");
            valid = false;
        }

        if (codeField.getText().isEmpty()){
            errorsLog.appendText("Kazdy liek musi obsahovat kod\n");
            valid = false;
        }

        if (batchField.getText().isEmpty()){
            errorsLog.appendText("Kazdy liek musi byt v nejakej davke\n");
            valid = false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        try {
            dateFormat.parse(expirationField.getText());
            dateFormat.parse(addedField.getText());
            dateFormat.parse(soldField.getText());
        } catch (ParseException e){
            errorsLog.appendText("Zle zadany format datumu\n");
            valid = false;
        }

        if (saleCategoriesField.getText().isEmpty()){
            errorsLog.appendText("predajna kateogria nesmie byt prazdna\n");
            valid = false;

        }

        if (medicamentCategoriesField.getText().isEmpty()){
            errorsLog.appendText("kategoria lieku nesmie byt prazdna\n");
            valid = false;

        }

        if (stateField.getText().isEmpty()){
            errorsLog.appendText("liek musi mat stav\n");
            valid = false;

        }

        if (buyoutPriceField.getText().isEmpty()){
            errorsLog.appendText("nakupna cena musi byt vyplnena\n");
            valid = false;

        }

        if (sellingPriceField.getText().isEmpty()){
            errorsLog.appendText("predajna cena musi byt vyplnena\n");
            valid = false;

        }

        try{
            BigDecimal bigDecimal = new BigDecimal(buyoutPriceField.getText());
            bigDecimal = new BigDecimal(sellingPriceField.getText());
        }catch (NumberFormatException e){
            errorsLog.appendText("Zle zadana suma\n");
            valid = false;

        }

        return valid;
    }

}
