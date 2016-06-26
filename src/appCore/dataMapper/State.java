package appCore.dataMapper;

import javax.persistence.*;

@Entity
@Table(name = "states")
@NamedQuery(name = "find state by title", query = "SELECT s from State s where s.title=:title")
public class State {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    public int stateID;

    public String title;



}
