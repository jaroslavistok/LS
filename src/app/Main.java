package app;

import app.dataMapper.Managing;
import app.dataMapper.MedicamentInformation;
import app.transactionScript.rowGateways.MedicamentCategory;
import app.transactionScript.rowGateways.Price;
import app.transactionScript.rowGateways.SaleCategory;

import java.math.BigDecimal;

public class Main {


    public static void main(String[] args){


        Price price = new Price();
        price.patient = BigDecimal.valueOf(200);
        price.insert();
        price.insert();
        System.out.println(price.lastInsertedPriceID);

        Managing managing = new Managing();
        managing.insert();

        /*
        try {

            ConnectionManager.getConnection().setAutoCommit(false);
            Selects.addNewSaleCategory("volnopredajne", "Lieky predajne bez receptu");
            Selects.addNewSaleCategory("na_recept", "Lieky predajne iba na recept");
            Selects.addNewSaleCategory("nepredajne", "lieky po expiracnej dobe, alebo inym sposobom vyradene lieky");

            Selects.addNewMedicamentCategory("antibiotika", "Antibiotika");
            Selects.addNewMedicamentCategory("vitaminy", "Vitaminy");
            Selects.addNewMedicamentCategory("opiatika", "Opiaty");
            Selects.addNewMedicamentCategory("vyzivove doplnky", "doplnky");
            Selects.addNewMedicamentCategory("suroviny", "suroviny");


            Selects.addNewPlace("sklad");
            Selects.addNewPlace("laboratorium");

            SaleCategory saleCategory = SaleCategory.findByName("predajny");
            System.out.println(saleCategory.additionalInformation);

            MedicamentCategory medicamentCategory = MedicamentCategory.findByTitle("sirup");
            System.out.println(medicamentCategory.additionalInformation);

            MedicamentInformation medicamentInformation = new MedicamentInformation();


            //Selects.addNewMedicament("123", "code", "title");

            //ConnectionManager.getConnection().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        */


    }

}
