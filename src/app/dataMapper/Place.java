package app.dataMapper;

import javax.persistence.*;

@Entity
@Table(name = "place")
public class Place {
    @Id @GeneratedValue
    @Column(name = "place_id")
    public int placeID;

    public String title;



}
