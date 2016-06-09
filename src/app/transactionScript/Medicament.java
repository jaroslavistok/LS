package app.transactionScript;

public class Medicament implements RowDataGateway {

    static Medicament find(int medicamentID){
        return null;
    }

    public int medicamentID;
    public String title;
    public String code;

    public int idSaleCategory;
    public int idRecipe;
    public int idPrice;

    @Override
    public void insert(){

    }

    @Override
    public void update(){

    }

    @Override
    public void delete(){

    }

}
