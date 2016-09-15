package appCore.domainModel;

import appCore.dataMapper.Medicament;
import appCore.dataMapper.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Selects {
    /**
     * selects all medicaments from db
     */
    public List<Medicament> getAllMedicaments(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT e from Medicament e");
        List<Medicament> medicamentList = (List<Medicament>)query.getResultList();
        for (Medicament medicament : medicamentList){
            System.out.println();
        }

        entityManager.getTransaction().commit();
        return medicamentList;
    }

    public class Pair{
        public String title;
        public String batch;
    }

    /**
     * Selects all recipes from db
     */
    public List<Recipe> getAllRecipes(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT e from Recipe e");
        List<Recipe> recipeList = (List<Recipe>) query.getResultList();
        for (Recipe recipe : recipeList){
            System.out.println(recipe);
        }
        entityManager.getTransaction().commit();
        return recipeList;
    }


}
