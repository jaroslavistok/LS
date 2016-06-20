package appCore.dataMapper;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Price class, maps prices table in database
 */
@Entity
@Table(name="prices")
public class Price {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    public int priceId;

    @Column(name = "purchase_price")
    public BigDecimal purchasePrice;

    @Column(name = "insurance_company")
    public BigDecimal insuranceCompany;

    @Column(name = "pharmacy_profit")
    public double pharmacyProfit;

    @Column(name = "buyout_price")
    public BigDecimal buyoutPrice;

    public BigDecimal patient;
    public double dph;
    public double discount;

    @Column(name = "selling_price")
    public BigDecimal seelingPrice;


}
