package app.dataMapper;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name="lab")
public class Lab {
    @Id
    @Column(name = "store_id")
    public int storeId;

    public Date inserted;

    @OneToMany(mappedBy = "lab")
    Collection<Medicament> medicaments;

}
