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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicament_id")
    public int medicamentId;

    public String batch;
    public String title;
    public String code;

    @ManyToMany
    @JoinTable(name="in_medicament_category",
    joinColumns = @JoinColumn(name = "medicament_id"),
    inverseJoinColumns = @JoinColumn(name="medicament_category_id"))
    public Collection<MedicamentCategory> medicament;

    @OneToMany(mappedBy = "medicament")
    public Collection<Recipe> recipes;

    @ManyToOne
    @JoinColumn(name = "sale_category_id")
    public SaleCategory saleCategory;


    @OneToOne
    @JoinColumn(name="place_id")
    Place place;

    @OneToOne
    @JoinColumn(name = "price_id")
    Price price;

    public Medicament(){

    }

    public Medicament(String batch, String title, String code){
        this.batch = batch;
        this.title = title;
        this.code = code;
    }

}
