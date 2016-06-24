package appCore.dataMapper;
import javax.persistence.*;
import java.util.Collection;

/**
 * Medicament class, maps medicamentCategories table in database
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
    public Collection<MedicamentCategory> medicamentCategories;


    @ManyToOne
    @JoinColumn(name = "sale_category_id")
    public SaleCategory saleCategory;


    @OneToOne
    @JoinColumn(name="state_id")
    public State state;

    @OneToOne
    @JoinColumn(name = "price_id")
    public Price price;

    @OneToOne
    @JoinColumn(name= "medicament_information_id")
    public MedicamentInformation medicamentInformation;

    @OneToMany(mappedBy = "medicament")
    public Collection<PriceHistory> histories;



    public Medicament(){

    }

    public Medicament(String batch, String title, String code){
        this.batch = batch;
        this.title = title;
        this.code = code;
    }

    @Override
    public String toString(){
        return String.format("%s", title);
    }

}
