package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represent sale kategory from sale_catgories table in databse
 */
public class SaleCategory implements RowDataGateway {
    public int saleCategoryID;
    public String title;
    public String additionalInformation;

    /**
     * finds row represented by this class in sales catgories table
     * return object which represents this row from table
     * otherwise return null
     *
     */
    public static SaleCategory findById(int saleCategoryID){
        SaleCategory saleCategory = new SaleCategory();
        String sql = "SELECT * FROM sale_categories WHERE sale_category_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, String.valueOf(saleCategoryID));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            saleCategory.title = resultSet.getString("title");
            saleCategory.additionalInformation = resultSet.getString("additional_information");
            saleCategory.saleCategoryID = resultSet.getInt("sale_category_id");

            resultSet.close();
            ConnectionManager.close();
            return saleCategory;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts new category represented by this class to he database
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO sale_categories (title, additional_information)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, additionalInformation);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates sale category represented by this class in database
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE sale_categories SET title=?, additional_information=?" +
                    "WHERE sale_category_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, additionalInformation);
            preparedStatement.setInt(3, saleCategoryID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes sale category represented by this class from database
     */
    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM sale_categories " +
                    "WHERE sale_category_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, saleCategoryID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
