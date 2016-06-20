package appCore.dataMapper;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "medicament_information")
public class MedicamentInformation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicament_information_id")
    public int medicamentInformationID;

    public Date expiration;
    public Date sold;
    public Date added;



}
