package appCore.transactionScript.rowGateways;

import appCore.transactionScript.db.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents single row from table Recipes
 */
public class Recipe implements RowDataGateway {
    public int recipeID;
    public Date date;
    public int cashRegisterNumber;
    public int number;
    public int medicamentID;
    public String type;
    public int recipeBatchID;


    public static List<Recipe> getAllRecipes(){
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes";

        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Recipe recipe = new Recipe();
                recipe.recipeID = resultSet.getInt("recipe_id");
                recipe.cashRegisterNumber = resultSet.getInt("cash_register_number");
                recipe.date = resultSet.getDate("date");
                recipe.number = resultSet.getInt("number");
                recipe.type = resultSet.getString("type");
                recipe.recipeBatchID = resultSet.getInt("recipe_batch_id");
                recipe.medicamentID = resultSet.getInt("medicament_id");
                recipes.add(recipe);
            }
            return recipes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * This method finds row in database by given ID
     * Return new Object of Recipe class taht represents this row
     * otherwise it returns null
     *
     */
    public static Recipe findById(int recipeId) {

        Recipe recipe = new Recipe();
        String query = "SELECT * FROM recipes WHERE recipe_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, String.valueOf(recipeId));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            recipe.recipeID = resultSet.getInt("recipe_id");
            recipe.date = resultSet.getDate("date");
            recipe.medicamentID = resultSet.getInt("medicament_id");
            recipe.number = resultSet.getInt("number");
            recipe.cashRegisterNumber = resultSet.getInt("cash_register_number");
            recipe.type = resultSet.getString("type");
            recipe.recipeBatchID = resultSet.getInt("recipe_batch_id");

            resultSet.close();
            ConnectionManager.close();
            return recipe;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert new row represented by this class to the database
     *
     * Constraint: medicamentID must exists in table medicamentCategories
     * otherwise this method will not work
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO recipes (date, cash_register_number, number, medicament_id, type)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, cashRegisterNumber);
            preparedStatement.setInt(3, number);
            preparedStatement.setInt(4, medicamentID);
            preparedStatement.setString(5, type);
            //preparedStatement.setInt(6, recipeBatchID);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                recipeID = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates row represented by this class and sets new values
     */
    @Override
    public void update() {
        String sql = "UPDATE recipes SET date=?, cash_register_number=?, number=?, medicament_id=?, type=?, recipe_batch_id=?" +
                "WHERE recipe_id=?";
        try {
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, cashRegisterNumber);
            preparedStatement.setInt(3, number);
            preparedStatement.setInt(4, medicamentID);
            preparedStatement.setString(5, type);
            preparedStatement.setInt(6, recipeBatchID);
            preparedStatement.setInt(7, recipeID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes row represented by this class from database
     *
     * Recipe id should exists, otherwise nothing will be deleted
     */
    @Override
    public void delete() {
        String sql = "DELETE FROM recipes WHERE recipe_id=?";
        try {
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, recipeID);
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
