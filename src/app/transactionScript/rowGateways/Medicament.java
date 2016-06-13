package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.sql.*;

/**
 * Represents row from table Medicament
 */
public class Medicament implements RowDataGateway {
    public int medicamentID;
    public Integer saleCategoryID;
    public String title;
    public String batch;
    public String code;
    public Date expiration;
    public int count;


    /**
     * Factory method
     * Finds medicament in medicaments table by its ID
     * Returns null if not found, otherwise returns new Medicament object
     * with data from database
     */
    public static Medicament findByID(int medicamentID) {
        Medicament medicament = new Medicament();

        String query = "SELECT * FROM medicaments WHERE medicament_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, medicamentID);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            medicament.medicamentID = resultSet.getInt("medicament_id");
            medicament.saleCategoryID = resultSet.getInt("sale_category_id");
            medicament.title = resultSet.getString("title");
            medicament.batch = resultSet.getString("batch");
            medicament.code = resultSet.getString("code");
            medicament.count = resultSet.getInt("count");
            medicament.expiration = resultSet.getDate("expiration");

            resultSet.close();
            ConnectionManager.close();
            return medicament;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method insert new row which is represented by this class to the database
     *
     * Constraint: saleCategoryID MUST exists, otherwise, row will not be inserted
     *
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO medicaments (title, batch, code, count, " +
                    "sale_category_id, expiration)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, batch);
            preparedStatement.setString(3, code);
            preparedStatement.setInt(4, count);
            if (saleCategoryID == null)
                preparedStatement.setNull(5, Types.INTEGER);
            else
                preparedStatement.setInt(5, saleCategoryID);
            preparedStatement.setDate(6, expiration);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates row represented by this class in database
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE medicaments SET title=?, batch=?, " +
                    "code=?, count=?, expiration=?, sale_category_id=? WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, batch);
            preparedStatement.setString(3, code);
            preparedStatement.setInt(4, count);
            preparedStatement.setDate(5, expiration);
            if (saleCategoryID == null)
                preparedStatement.setNull(6, Types.INTEGER);
            else
                preparedStatement.setInt(6, saleCategoryID);
            preparedStatement.setInt(7, medicamentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method deletes row represented by this class from database
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM medicaments WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, medicamentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}