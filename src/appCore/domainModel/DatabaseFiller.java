package appCore.domainModel;

import appCore.dataMapper.State;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DatabaseFiller {

    public void createSomePlaces(){
        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        em.getTransaction().begin();

        State laboratory = new State();
        laboratory.title = "laboratory";
        State warehouse = new State();
        warehouse.title = "warehouse";

        em.persist(laboratory);
        em.persist(warehouse);

        em.close();
        em.getTransaction().commit();
    }



}
