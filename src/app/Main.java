package app;

import app.dataMapper.Managing;
import app.domainModel.DatabaseFiller;
import app.transactionScript.db.ConnectionManager;
import app.transactionScript.rowGateways.Medicament;
import app.transactionScript.transactionScripts.CrudOperations;

public class Main {


    public static void main(String[] args){


        Managing managing = new Managing();
        managing.insert();

        try {
            ConnectionManager.getConnection().setAutoCommit(false);
            CrudOperations.addNewSaleCategory("volnopredajne", "Lieky predajne bez receptu");
            CrudOperations.addNewSaleCategory("na_recept", "Lieky predajne iba na recept");
            CrudOperations.addNewSaleCategory("nepredajne", "lieky po expiracnej dobe, alebo inym sposobom vyradene lieky");

            CrudOperations.addNewMedicamentCategory("antibiotika", "Antibiotika");
            CrudOperations.addNewMedicamentCategory("vitaminy", "Vitaminy");
            CrudOperations.addNewMedicamentCategory("opiatika", "Opiaty");
            CrudOperations.addNewMedicamentCategory("vyzivove doplnky", "doplnky");
            CrudOperations.addNewMedicamentCategory("suroviny", "suroviny");


            CrudOperations.addNewPlace("sklad");
            CrudOperations.addNewPlace("laboratorium");


            //CrudOperations.addNewMedicament("123", "code", "title");

            ConnectionManager.getConnection().commit();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
