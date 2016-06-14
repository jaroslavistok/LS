package app.transactionScript.rowGateways;

import java.sql.Date;

public class MedicamentInformation implements RowDataGateway{

    public int medicamentInformationID;
    public Date added;
    public Date expiration;
    public Date sold;

    @Override
    public void insert() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
