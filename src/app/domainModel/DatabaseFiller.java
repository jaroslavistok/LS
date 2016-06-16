package app.domainModel;

import app.dataMapper.Place;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DatabaseFiller {

    public void createSomePlaces(){
        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        em.getTransaction().begin();

        Place laboratory = new Place();
        laboratory.title = "laboratory";
        Place warehouse = new Place();
        warehouse.title = "warehouse";

        em.persist(laboratory);
        em.persist(warehouse);

        em.close();
        em.getTransaction().commit();
    }



}
