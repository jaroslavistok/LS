package app.dataMapper;

import javax.persistence.*;
import java.util.Collection;

/**
 * MedicamentCategory class, maps medicament_category table in database
 */
@Entity
@Table(name="medicaments_categories")
public class MedicamentCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int medicament_category_id;

    public String title;

    @Column(name = "additional_information")
    public String additionalInformation;


}
