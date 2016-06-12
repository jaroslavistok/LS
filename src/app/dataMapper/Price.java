package app.dataMapper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name="prices")
public class Price {
    @Id @GeneratedValue
    @Column(name = "price_id")
    public int priceId;

    @Column(name = "purchase_price")
    public BigDecimal purchasePrice;

    @Column(name = "insurance_company")
    public BigDecimal insuranceCompany;

    @Column(name = "pharmacy_profit")
    public double pharmacyProfit;

    public BigDecimal patient;
    public double dph;
    public double discount;

    @OneToOne
    Medicament medicament;

}
