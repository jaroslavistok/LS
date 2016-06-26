package appCore.dataMapper;


import javax.persistence.*;
import java.util.Collection;

/**
 * RecipesBatch class, maps recipe_batches table in database
 */
@Entity
@Table(name = "recipes_batches")
@NamedQuery(name = "find by number", query = "SELECT r FROM RecipesBatch r WHERE r.number=:number")
public class RecipesBatch {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_batch_id")
    public int recipeBatchId;

    public String title;
    public String abbreviation;
    public int number;

    @OneToMany(mappedBy = "recipesBatch")
    Collection<Recipe> recipes;

    @Override
    public String toString(){
        return String.format("%d", number);
    }

}
