package app.dataMapper;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Medicament class, maps medicament table in database
 */
@Entity
@Table(name="medicaments")
public class Medicament {
    @Id @GeneratedValue
    @Column(name = "medicament_id")
    public int medicamentId;

    public String batch;
    public String title;
    public String code;
    public int count;
    public Date expiration;


    @ManyToMany
    @JoinTable(name="in_medicament_category",
    joinColumns = @JoinColumn(name = "medicament_id"),
    inverseJoinColumns = @JoinColumn(name="medicament_category_id"))
    public Collection<MedicamentCategory> medicament;

    @OneToMany(mappedBy = "medicament")
    public Collection<Recipe> recipes;

    @ManyToOne
    public Store store;

    @ManyToOne
    public Lab lab;

    @ManyToOne
    @JoinColumn(name = "sold_medicament_id")
    public SoldMedicament soldMedicament;

    @ManyToOne
    @JoinColumn(name = "sale_category_id")
    public SaleCategory saleCategory;




}
