package app.dataMapper;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Managing {

    public void insert(){

        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        em.getTransaction().begin();

        Medicament medicament = new Medicament();
        MedicamentCategory medicamentCategory = new MedicamentCategory();
        Price price = new Price();
        Recipe recipe = new Recipe();
        Store store = new Store();
        Lab lab = new Lab();
        SoldMedicament soldMedicament = new SoldMedicament();
        RecipeBatch recipeBatch = new RecipeBatch();
        em.persist(medicament);
        em.persist(medicamentCategory);
        em.persist(price);
        em.persist(store);
        em.persist(recipe);
        em.persist(lab);
        em.persist(soldMedicament);
        em.persist(recipeBatch);

        em.getTransaction().commit();

    }

}
