package app.dataMapper;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Recipe class, maps recipes table in database
 */
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id @GeneratedValue
    @Column(name = "recipe_id")
    public int recipeID;

    public Date date;

    @Column(name = "cash_register_number")
    public int cashRegisterNumber;
    public int number;
    public String type;

    @ManyToOne
    @JoinColumn(name = "medicament_id")
    Medicament medicament;


    @ManyToOne
    @JoinColumn(name="recipe_batch_id")
    RecipeBatch recipeBatch;

}
