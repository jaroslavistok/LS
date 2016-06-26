package guiDM;

import appCore.dataMapper.*;
import appCore.domainModel.Stats;
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
import java.util.Date;
import java.util.HashSet;
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

    @FXML
    TextArea errorsLog;

    appCore.dataMapper.Medicament insertedMedicament;

    public appCore.dataMapper.Medicament getMedicament() {
        return insertedMedicament;
    }

    //private Medicament medicamentCategories;

    /**
     * veryfi and saves given information to the database
     */
    public void handleOkButtonAction(ActionEvent event) {
        if (!verifyInputs()){
            return;
        }

        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();
        Price price = new Price();
        entityManager.persist(price);
        price.seelingPrice = new BigDecimal(sellingPriceField.getText());
        price.buyoutPrice = new BigDecimal(buyoutPriceField.getText());
        entityManager.getTransaction().commit();

        MedicamentInformation medicamentInformation = new MedicamentInformation();
        entityManager.getTransaction().begin();
        entityManager.persist(medicamentInformation);
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
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Find category by title", SaleCategory.class);
        query.setParameter("title", saleCategoriesField.getText());
        SaleCategory saleCategory;
        try {
            saleCategory = (SaleCategory) query.getSingleResult();
        }catch (NoResultException e){
            saleCategory = new SaleCategory();
            entityManager.persist(saleCategory);
            saleCategory.title = saleCategoriesField.getText();
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        query = entityManager.createNamedQuery("find by name", MedicamentCategory.class);
        query.setParameter("title", medicamentCategoriesField.getText());
        MedicamentCategory medicamentCategory;
        try {
            medicamentCategory = (MedicamentCategory) query.getSingleResult();
        }catch (NoResultException e){
            medicamentCategory = new MedicamentCategory();
            entityManager.persist(medicamentCategory);
            medicamentCategory.title =medicamentCategoriesField.getText();
        }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Query query1 = entityManager.createNamedQuery("find state by title", State.class);
        query1.setParameter("title", titleField.getText());
        State state;
        try{
            state = (State) query1.getSingleResult();
        }catch (NoResultException e){
            state = new State();
            entityManager.persist(state);
            state.title = titleField.getText();
        }
        entityManager.getTransaction().commit();


        entityManager.getTransaction().begin();
        Medicament medicament = new Medicament();
        entityManager.persist(medicament);
        medicament.medicamentCategories = new HashSet<>();
        medicament.medicamentCategories.add(medicamentCategory);
        medicament.price = price;
        medicament.saleCategory =saleCategory;
        medicament.medicamentInformation = medicamentInformation;
        medicament.title = titleField.getText();
        medicament.code = codeField.getText();
        medicament.batch = batchField.getText();
        medicament.state = state;

        entityManager.getTransaction().commit();
        System.out.println("Added");
        insertedMedicament = medicament;
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
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
