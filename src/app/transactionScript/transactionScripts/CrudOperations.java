package app.transactionScript.transactionScripts;

import app.dataMapper.Price;
import app.transactionScript.rowGateways.*;

import java.math.BigDecimal;
import java.sql.Date;

public class CrudOperations {

    public static void addNewSaleCategory(String title, String additionalInformation){
        SaleCategory saleCategory = new SaleCategory();
        saleCategory.title = title;
        saleCategory.additionalInformation = additionalInformation;
        saleCategory.insert();
    }

    public static void addNewPlace(String title){
        Place place = new Place();
        place.title = title;
        place.insert();

    }

    public static void addNewMedicamentCategory(String title, String additionalInformation){
        MedicamentCategory medicamentCategory = new MedicamentCategory();
        medicamentCategory.title = title;
        medicamentCategory.additionalInformation = additionalInformation;
        medicamentCategory.insert();
    }

    public static void addNewMedicament(String batch, String code, String title, BigDecimal buyoutPrice,
                                        double discount, double dph, BigDecimal insuranceCompany, BigDecimal patient,
                                        double pharmacyProfit, BigDecimal purchasePrice, BigDecimal sellingPrice,
                                        String medicamentCategory, String saleCategory){
        Medicament medicament = new Medicament();
        medicament.batch = batch;
        medicament.code = code;
        medicament.title = title;
        medicament.insert();

    }

    public static void addNewRecipeBatch(String abbreviation, int number, String title){
        RecipesBatch recipesBatch = new RecipesBatch();
        recipesBatch.abbreviation = abbreviation;
        recipesBatch.number = number;
        recipesBatch.title = title;
    }

    public static void addNewMedicamentInformation(Date added, Date expiration, Date sold){
        MedicamentInformation medicamentInformation = new MedicamentInformation();
        medicamentInformation.added = added;
        medicamentInformation.sold = sold;
        medicamentInformation.expiration = expiration;
    }





}
