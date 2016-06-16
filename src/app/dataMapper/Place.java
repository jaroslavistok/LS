package app.dataMapper;

import javax.persistence.*;

@Entity
@Table(name = "places")
public class Place {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    public int placeID;

    public String title;



}
