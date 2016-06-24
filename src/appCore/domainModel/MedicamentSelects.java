package appCore.domainModel;

import appCore.dataMapper.Medicament;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class MedicamentSelects {
    public List<Medicament> getAllMedicaments(){
        EntityManager entityManager = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT e from Medicament e");
        List<Medicament> medicamentList = (List<Medicament>) query.getResultList();
        for (Medicament medicament : medicamentList){
            System.out.println(medicament);
        }

        entityManager.getTransaction().commit();
        return medicamentList;
    }


}
