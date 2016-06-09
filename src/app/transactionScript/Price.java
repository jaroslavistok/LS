package app.transactionScript;

import java.math.BigDecimal;

public class Price implements RowDataGateway {
    public int priceID;
    public BigDecimal purchasePrice;
    public BigDecimal salePrice;
    public int sale;
    public BigDecimal patient;
    public BigDecimal insuranceCompany;
    public BigDecimal additionalCharge;


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
