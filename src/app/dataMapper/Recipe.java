package app.dataMapper;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id @GeneratedValue
    @Column(name = "recipe_id")
    public int recipeID;

    public Date date;
    public int cashRegisterNumber;
    public int number;
    public String type;

    @ManyToOne
    @JoinColumn(name = "medicament_id")
    Medicament medicament;


    @ManyToOne
    RecipeBatch recipeBatch;

}
