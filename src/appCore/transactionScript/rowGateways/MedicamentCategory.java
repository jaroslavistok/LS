package appCore.transactionScript.rowGateways;

import appCore.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents medicament category from medicaments_categories table
 */
public class MedicamentCategory implements RowDataGateway {
    public int medicamentCategoryID;
    public String title;
    public String additionalInformation;
    public int lastInsertedID;

    /**
     * finds medicament category by id and returns object, that represents
     * this row from databse, otherwise return null
     *
     */
    public static MedicamentCategory findById(int medicamentCategoryID){
        MedicamentCategory medicamentCategory = new MedicamentCategory();

        String query = "SELECT * FROM medicaments_categories WHERE medicament_category_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentCategoryID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            medicamentCategory.medicamentCategoryID = resultSet.getInt("medicament_category_id");
            medicamentCategory.title = resultSet.getString("title");
            medicamentCategory.additionalInformation = resultSet.getString("additional_information");
            resultSet.close();
            ConnectionManager.close();
            return medicamentCategory;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static MedicamentCategory findByTitle(String title){
        MedicamentCategory medicamentCategory = new MedicamentCategory();

        String query = "SELECT * FROM medicaments_categories WHERE title=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            medicamentCategory.medicamentCategoryID = resultSet.getInt("medicament_category_id");
            medicamentCategory.title = resultSet.getString("title");
            medicamentCategory.additionalInformation = resultSet.getString("additional_information");
            resultSet.close();
            ConnectionManager.close();
            return medicamentCategory;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Inserts new medicament category represented by this class to the
     * database
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO medicaments_categories (title, additional_information)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, additionalInformation);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                lastInsertedID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates medicament category represented by this class in database
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE medicaments_categories SET title=?, additional_information=?" +
                    "WHERE medicament_category_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, additionalInformation);
            preparedStatement.setInt(3, medicamentCategoryID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove medicament category represented by this cass from database
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM medicaments_categories WHERE medicament_category_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentCategoryID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
