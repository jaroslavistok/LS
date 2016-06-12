package app;

import app.dataMapper.Managing;
import app.transactionScript.rowGateways.Medicament;
import app.transactionScript.transactionScripts.CrudOperations;

public class Main {


    public static void main(String[] args){

        Managing managing = new Managing();
        managing.insert();

    }

}
