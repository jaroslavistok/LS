package app.dataMapper;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Store class, maps store table in database
 */
@Entity
public class Store {
    @Id @GeneratedValue
    @Column(name = "store_id")
    public int storeId;

    public Date inserted;

    @OneToMany(mappedBy = "store")
    Collection<Medicament> medicament;

}
