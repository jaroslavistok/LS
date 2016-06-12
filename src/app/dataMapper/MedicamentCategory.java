package app.dataMapper;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="medicaments_categories")
public class MedicamentCategory {
    @Id @GeneratedValue
    public int medicament_category_id;

    public String title;
    public String additionalInformation;


}
