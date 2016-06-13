package app.dataMapper;

import javax.persistence.*;
import java.util.Collection;

/**
 * Sale category class, maps sale_categories table in database
 */
@Entity
@Table(name = "sale_categories")
public class SaleCategory {
    @Id @GeneratedValue
    @Column(name = "sale_category_id")
    public int saleCategoryID;

    public String title;

    @Column(name = "additional_information")
    public String additionalInformation;

    @OneToMany
    public Collection<Medicament> medicaments;
}
