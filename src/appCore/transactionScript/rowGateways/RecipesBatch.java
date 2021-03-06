package appCore.transactionScript.rowGateways;

import appCore.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * represents row from table recipe_batches in database
 */
public class RecipesBatch implements RowDataGateway {

    public int recipeBatchId;
    public String abbreviation;
    public String title;
    public int number;

    /**
     * factory method, finds row represented by this class in recipes_bacthes table
     * by given id and returns obect of this class tahat represents this row
     *
     * otherwise it returns null
     */
    public static RecipesBatch findById(int recipeBatchId){
        RecipesBatch recipesBatch = new RecipesBatch();
        String sql = "SELECT * FROM recipes_batches WHERE recipe_batch_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, recipeBatchId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            recipesBatch.recipeBatchId = resultSet.getInt("recipe_batch_id");
            recipesBatch.abbreviation = resultSet.getString("abbreviation");
            recipesBatch.number = resultSet.getInt("number");
            recipesBatch.title = resultSet.getString("title");

            resultSet.close();
            ConnectionManager.close();
            return recipesBatch;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RecipesBatch findByNumber(int recipeBatchNumber){
        RecipesBatch recipesBatch = new RecipesBatch();
        String sql = "SELECT * FROM recipes_batches WHERE number=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, recipeBatchNumber);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            recipesBatch.recipeBatchId = resultSet.getInt("recipe_batch_id");
            recipesBatch.abbreviation = resultSet.getString("abbreviation");
            recipesBatch.number = resultSet.getInt("number");
            recipesBatch.title = resultSet.getString("title");

            resultSet.close();
            ConnectionManager.close();
            return recipesBatch;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method inserts new row represented by this class to the database
     *
     * constraint: recipe_id must exists otherwide this method will not work properly
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO recipes_batches (title, abbreviation, number)" +
                    "VALUES (?,?,?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, abbreviation);
            preparedStatement.setInt(3, number);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next())
                recipeBatchId = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method update row represented by this class in database
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE recipes_batches SET title=?, " +
                    "abbreviation=?, number=? WHERE recipe_batch_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, abbreviation);
            preparedStatement.setInt(3, number);
            preparedStatement.setInt(4, recipeBatchId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method delete row represented by thi class from database
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM recipes_batches WHERE recipe_batch_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, recipeBatchId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return String.format("%d", number);
    }
}
