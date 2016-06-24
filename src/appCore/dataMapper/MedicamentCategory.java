package appCore.dataMapper;

import javax.persistence.*;

/**
 * MedicamentCategory class, maps medicament_category table in database
 */
@Entity
@Table(name="medicaments_categories")
@NamedQuery(name = "find by name",
query = "select mc from MedicamentCategory mc where mc.title=:title")
public class MedicamentCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int medicament_category_id;

    public String title;

    @Column(name = "additional_information")
    public String additionalInformation;


}
