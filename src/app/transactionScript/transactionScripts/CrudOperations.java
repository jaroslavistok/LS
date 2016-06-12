package app.transactionScript.transactionScripts;

import app.transactionScript.rowGateways.Medicament;

import java.sql.Date;

public class CrudOperations {
    public static void insertNewMedicamentToTheStore(){
        Medicament medicament = new Medicament();

        medicament.medicamentID = 6;
        medicament.batch = "C6";
        medicament.saleCategoryID = 0;
        medicament.title = "Medicament";
        medicament.code = "CKSKSK";
        medicament.saleCategoryID = null;
        medicament.expiration = new Date(System.currentTimeMillis());

        medicament.update();
    }




}
