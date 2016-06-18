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
        RecipesBatch recipesBatch = new RecipesBatch();
        SaleCategory saleCategory= new SaleCategory();
        State state = new State();
        MedicamentInformation medicamentInformation = new MedicamentInformation();

        em.persist(medicamentInformation);
        em.persist(state);
        em.persist(medicament);
        em.persist(medicamentCategory);
        em.persist(price);
        em.persist(recipe);
        em.persist(recipesBatch);
        em.persist(saleCategory);

        em.getTransaction().commit();

    }

}
