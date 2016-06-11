package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class represent sold medicament, e.g. row from table sold_medicaments
 */
public class SoldMedicament implements RowDataGateway {
    public int medicamentID;
    public Date sold;


    /**
     * finds item in sold items table in databse and returns object that represents this item
     * otherwise it returns null
     *
     */
    public static SoldMedicament findById(int medicamentID){
        SoldMedicament soldMedicament = new SoldMedicament();

        String query = "SELECT * FROM sold_medicaments WHERE medicament_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            soldMedicament.medicamentID = resultSet.getInt("medicament_id");
            soldMedicament.sold = resultSet.getDate("inserted");
            resultSet.close();
            ConnectionManager.close();
            return soldMedicament;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;


    }

    /**
     * Inserts new medicament to the sold medicaments
     *
     * Constraints: medicament id must exists otherwise this
     * method will not work properly
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO sold_medicaments (medicament_id, sold)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentID);
            preparedStatement.setDate(2, sold);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates date when medicament was sold in table sold medicaments
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE sold_medicaments SET sold=?" +
                    "WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, sold);
            preparedStatement.setInt(2, medicamentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methos deletes sold medicament from sold_medicaments table form db
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM sold_medicaments WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
