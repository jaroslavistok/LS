package appCore.transactionScript.rowGateways;

import appCore.transactionScript.db.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents row from table Medicament
 */
public class Medicament implements RowDataGateway {
    // primary key
    public int medicamentID;

    //foreign keys
    public Integer saleCategoryId;
    public int stateID;
    public int priceID;
    public int medicamentInformationID;


    //other columns
    public String title;
    public String batch;
    public String code;

    /**
     * Factory method
     * Finds medicamentCategories in medicaments table by its ID
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
            medicament.saleCategoryId = resultSet.getInt("sale_category_id");
            medicament.stateID = resultSet.getInt("state_id");
            medicament.priceID = resultSet.getInt("price_id");
            medicament.medicamentInformationID = resultSet.getInt("medicament_information_id");

            medicament.title = resultSet.getString("title");
            medicament.batch = resultSet.getString("batch");
            medicament.code = resultSet.getString("code");




            resultSet.close();
            ConnectionManager.close();
            return medicament;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Medicament findBuTitle(String medicamentTitle) {
        Medicament medicament = new Medicament();

        String query = "SELECT * FROM medicaments WHERE title=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, medicamentTitle);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            medicament.medicamentID = resultSet.getInt("medicament_id");
            medicament.saleCategoryId = resultSet.getInt("sale_category_id");
            medicament.stateID = resultSet.getInt("state_id");
            medicament.priceID = resultSet.getInt("price_id");
            medicament.medicamentInformationID = resultSet.getInt("medicament_information_id");
            medicament.title = resultSet.getString("title");
            medicament.batch = resultSet.getString("batch");
            medicament.code = resultSet.getString("code");

            resultSet.close();
            ConnectionManager.close();
            return medicament;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Return array list of all medicaments
     */
    public static List<Medicament> getAllMedicaments(){
        List<Medicament> medicaments = new ArrayList<>();
        try{
            String query = "SELECT * FROM medicaments";
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Medicament newMedicament = Medicament.findByID(resultSet.getInt("medicament_id"));
                medicaments.add(newMedicament);
            }
            return medicaments;
        }catch (SQLException e){
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
            String sql = "INSERT INTO medicaments (title, batch, code," +
                    "sale_category_id, price_id, state_id, medicament_information_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, batch);
            preparedStatement.setString(3, code);
            preparedStatement.setInt(4, saleCategoryId);
            preparedStatement.setInt(5, priceID);
            preparedStatement.setInt(6, stateID);
            preparedStatement.setInt(7, medicamentInformationID);


            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                medicamentID = resultSet.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertWithoutForeignKeys() {
        try {
            String sql = "INSERT INTO medicaments (title, batch, code)" +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, batch);
            preparedStatement.setString(3, code);
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
                    "code=?, sale_category_id=?, price_id=?, state_id=?, medicament_information_id=? WHERE medicament_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, batch);
            preparedStatement.setString(3, code);
            preparedStatement.setInt(4, saleCategoryId);
            preparedStatement.setInt(5, priceID);
            preparedStatement.setInt(6, stateID);
            preparedStatement.setInt(7, medicamentInformationID);
            preparedStatement.setInt(8, medicamentID);
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


    @Override
    public String toString(){
        return String.format("%s", title);
    }
}
