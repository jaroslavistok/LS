package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents one item from store
 */
public class MedicamentFromStore implements RowDataGateway {

    public int medicamentId;
    public Date inserted;

    /**
     * Finds item in store by its id and returns new object of this class
     * which represents row from database
     */
    public static MedicamentFromStore findById(int medicamentId){
        MedicamentFromStore store = new MedicamentFromStore();

        String query = "SELECT * FROM lab WHERE medicament_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            store.medicamentId = resultSet.getInt("medicament_id");
            store.inserted = resultSet.getDate("inserted");
            resultSet.close();
            ConnectionManager.close();
            return store;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Inserts new item represented by this class to the database
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO store (medicament_id, inserted)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentId);
            preparedStatement.setDate(2, inserted);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update date inserted in database on item represented by this class
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE store SET inserted=?" +
                    "WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, inserted);
            preparedStatement.setInt(2, medicamentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes item represented by thid object from database
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM store WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
