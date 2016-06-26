package appCore.domainModel;

import appCore.dataMapper.Medicament;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;

public class Stats {
    public long getNumberOfMedicaments(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select count(d) from Medicament d");
        long result = (long) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public long getNumberOfMedicamentsInStore(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select count(d) from Medicament d" +
                " inner join State s ON d.state.stateID=s.stateID WHERE s.title='na_sklade'");
        long result = (long) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public long getNumberOfMedicamentsInLab(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select count(d) from Medicament d" +
                " inner join State s ON d.state.stateID=s.stateID WHERE s.title='lab'");
        long result = (long) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public long getNumberOfSoldMedicaments(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select count(d) from Medicament d" +
                " inner join State s ON d.state.stateID=s.stateID WHERE s.title='sold'");
        long result = (long) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public long getNumberOfExpiredMedicaments(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select count(d) from Medicament d" +
                " inner join MedicamentInformation m ON d.medicamentInformation.medicamentInformationID=m.medicamentInformationID " +
                "WHERE m.expiration > CURRENT_DATE");
        long result = (long) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public BigDecimal getTotalMoneyValueOfAllMedicaments(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select sum(p.buyoutPrice) from Price p");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public BigDecimal getTotalMoneyValueOfAllMedicamentsInStore(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select sum(p.buyoutPrice) from Price p inner join Medicament m on m.price.priceId=p.priceId inner join State s on s.stateID=m.state.stateID WHERE s.title='na_sklade'");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public BigDecimal getTotalMoneyValueOfAllMedicamentsInLab(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select sum(p.buyoutPrice) from Price p inner join Medicament m on m.price.priceId=p.priceId inner join State s on s.stateID=m.state.stateID WHERE s.title='lab'");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }


}
