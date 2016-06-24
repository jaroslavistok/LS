package appCore.transactionScript.transactionScripts;

import appCore.transactionScript.rowGateways.MedicamentCategory;

public class DataGenerator {

    public static void populateMedicamentCategories(){
        MedicamentCategory medicamentCategory = new MedicamentCategory();
        medicamentCategory.title = "Antibiotikum";
        medicamentCategory.additionalInformation = "Information about antibiotics";
        medicamentCategory.insert();




    }


}
