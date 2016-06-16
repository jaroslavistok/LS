package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicamentInformation implements RowDataGateway{

    public int medicamentInformationID;
    public Date added;
    public Date expiration;
    public Date sold;

    public static MedicamentInformation findByID(int medicamentInformationID) {
        MedicamentInformation medicamentInformation = new MedicamentInformation();

        String query = "SELECT * FROM medicament_information WHERE medicament_information_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentInformationID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            medicamentInformation.medicamentInformationID = resultSet.getInt("medicament_information_id");
            medicamentInformation.added = resultSet.getDate("added");
            medicamentInformation.expiration = resultSet.getDate("expiration");
            medicamentInformation.sold = resultSet.getDate("sold");

            resultSet.close();
            ConnectionManager.close();
            return medicamentInformation;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void insert() {

        try {
            String sql = "INSERT INTO medicament_information (added, expiration, sold)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, added);
            preparedStatement.setDate(2, expiration);
            preparedStatement.setDate(3, sold);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        try {
            String sql = "UPDATE medicament_information SET added=?, expiration=?, sold=?" +
                    "WHERE medicament_information_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, added);
            preparedStatement.setDate(2, expiration);
            preparedStatement.setDate(3, sold);
            preparedStatement.setInt(4, medicamentInformationID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM medicament_information WHERE medicament_information_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentInformationID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
