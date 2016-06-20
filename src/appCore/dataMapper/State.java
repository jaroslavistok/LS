package appCore.dataMapper;

import javax.persistence.*;

@Entity
@Table(name = "states")
public class State {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    public int stateID;

    public String title;



}
