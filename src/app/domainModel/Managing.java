package app.domainModel;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Managing {

    public void insert(){

        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        em.getTransaction().begin();
        Medicament medicament = new Medicament();
        em.persist(medicament);
        em.getTransaction().commit();

    }

}
