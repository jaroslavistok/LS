package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class represent one item from lab table from database
 */
public class MedicamentFromLab implements RowDataGateway {
    public int medicamentID;
    public Date inserted;

    /**
     *
     * finds item in laboratory store by its id, items are medicaments
     */
    public static MedicamentFromLab findByID(int medicamentID){
        MedicamentFromLab itemFromLab = new MedicamentFromLab();

        String query = "SELECT * FROM lab WHERE medicament_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            itemFromLab.medicamentID = resultSet.getInt("medicament_id");
            itemFromLab.inserted = resultSet.getDate("inserted");
            resultSet.close();
            ConnectionManager.close();
            return itemFromLab;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Insert item represented by this class to the database
     *
     * constraints: medicamentId must exists, otherwise this method will not work properly
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO lab (medicament_id, inserted)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentID);
            preparedStatement.setDate(2, inserted);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates inserted date in row represented by this class
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE lab SET inserted=?" +
                    "WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, inserted);
            preparedStatement.setInt(2, medicamentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deleted medicament from laboratory
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM lab WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
