package app.dataMapper;


import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * SoldMedicament class, maps sold_medicamnts table in database
 */
@Entity
@Table(name = "sold_medicaments")
public class SoldMedicament {
    @Id
    @Column(name = "sold_medicament_id")
    public int soldeMedicamentID;

    public Date sold;

    @OneToMany(mappedBy = "soldMedicament")
    public Collection<Medicament> medicaments;

}
