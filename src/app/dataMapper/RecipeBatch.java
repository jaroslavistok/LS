package app.dataMapper;


import javax.persistence.*;
import java.util.Collection;

/**
 * RecipeBatch class, maps recipe_batches table in database
 */
@Entity
@Table(name = "recipes_batches")
public class RecipeBatch {
    @Id @GeneratedValue
    @Column(name = "recipe_batch_id")
    public int recipeBatchId;

    public String title;
    public String abbreviation;
    public int number;

    @OneToMany(mappedBy = "recipeBatch")
    Collection<Recipe> recipes;

}
