package app.transactionScript.rowGateways;


public class Place implements RowDataGateway {

    public int placeID;
    public String title;

    public static Place findById(int placeId){
        return null;
    }

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
