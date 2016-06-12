package app.dataMapper;
import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

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
    @JoinTable(name="in_medicament_category")
    public Collection<MedicamentCategory> medicament;

    @OneToMany(mappedBy = "medicament")
    public Collection<Recipe> recipes;

    @ManyToOne
    public Store store;

    @ManyToOne
    public Lab lab;

    @ManyToOne
    public SoldMedicament soldMedicament;


}
