package app.domainModel;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Managing {

    public void insert(){

        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        em.getTransaction().begin();
        MedicamentEntity medicament = new MedicamentEntity();
        em.persist(medicament);
        em.getTransaction().commit();

    }

}
