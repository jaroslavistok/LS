package app;

import app.transactionScript.db.ConnectionManager;
import app.transactionScript.rowGateways.Medicament;
import app.transactionScript.rowGateways.Recipe;

public class Main {


    public static void main(String[] args){

        Medicament medicament = Medicament.findByID(2);
        System.out.println(medicament.title);
    }

}
