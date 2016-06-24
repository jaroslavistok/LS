package appCore.transactionScript.rowGateways;

import appCore.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represent binding information for Medicament and medicaments categories
 */
public class InMedicamentCategory implements RowDataGateway {
    public int medicament_category_id;
    public int medicament_id;

    /**
     * finds binding by category id
     */
    public static InMedicamentCategory findByCategoryID(int medicamentCategoryId){
        InMedicamentCategory inCategoryBinding = new InMedicamentCategory();

        String query = "SELECT * FROM in_medicament_category WHERE medicament_category_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentCategoryId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;


            inCategoryBinding.medicament_category_id = resultSet.getInt("medicament_category_id");
            inCategoryBinding.medicament_id = resultSet.getInt("medicament_id");

            resultSet.close();
            ConnectionManager.close();
            return inCategoryBinding;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * finds binding by medicamentCategories id
     */
    public static InMedicamentCategory findByMedicamentId(int medicamentID){
        InMedicamentCategory inCategoryBinding = new InMedicamentCategory();

        String query = "SELECT * FROM in_medicament_category WHERE medicament_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            inCategoryBinding.medicament_category_id = resultSet.getInt("medicament_category_id");
            inCategoryBinding.medicament_id = resultSet.getInt("medicament_id");
            resultSet.close();
            ConnectionManager.close();
            return inCategoryBinding;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Insert new binding represented by this class to the database
     *
     * Constraints: both keys must exists, otherwise this method will not work properly
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO in_medicament_category (medicament_category_id, medicament_id)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicament_category_id);
            preparedStatement.setInt(2, medicament_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates ids in binding in database represented by this class
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE in_medicament_category SET medicament_category_id=? WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicament_category_id);
            preparedStatement.setInt(2, medicament_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove binding represented by this class from database
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM in_medicament_category WHERE medicament_category_id=? AND medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicament_category_id);
            preparedStatement.setInt(2, medicament_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
