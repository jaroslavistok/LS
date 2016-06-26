package appCore.dataMapper;

import javax.persistence.*;
import java.sql.Date;

/**
 * Recipe class, maps recipes table in database
 */
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    public int recipeID;

    public Date date;

    @Column(name = "cash_register_number")
    public int cashRegisterNumber;
    public int number;
    public String type;

    @OneToOne
    @JoinColumn(name = "medicament_id")
    public Medicament medicament;


    @ManyToOne
    @JoinColumn(name="recipe_batch_id")
    public RecipesBatch recipesBatch;


    @Override
    public String toString(){
        return String.format("%d", number);
    }

}
