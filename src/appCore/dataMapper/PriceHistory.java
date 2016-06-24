package appCore.dataMapper;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_history_id")
    public int priceHistoryId;

    public BigDecimal price;

    public java.sql.Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "medicament_id")
    public Medicament medicament;

}
