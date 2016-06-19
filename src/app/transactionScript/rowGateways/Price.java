package app.transactionScript.rowGateways;

import app.transactionScript.db.ConnectionManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents row from table prices
 */
public class Price implements RowDataGateway {
    public int priceID;
    public BigDecimal purchasePrice;
    public double pharmacyProfit;
    public BigDecimal insurance_company;
    public BigDecimal patient;
    public double dph;

    public int lastInsertedPriceID;

    /**
     * finds row in table prices by given id
     * returns new object that represents this row
     */
    public static Price findById(int priceID){
        Price price = new Price();
        String sql = "SELECT * FROM prices WHERE price_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, String.valueOf(priceID));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            price.priceID = resultSet.getInt("price_id");
            price.dph = resultSet.getDouble("dph");
            price.purchasePrice = resultSet.getBigDecimal("purchase_price");
            price.pharmacyProfit = resultSet.getDouble("pharmacy_profit");
            price.insurance_company = resultSet.getBigDecimal("insurance_company");
            price.patient = resultSet.getBigDecimal("patient");

            resultSet.close();
            ConnectionManager.close();
            return price;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getTotalValueOfAllMedicaments(){
        try{
            String sql = "SELECT sum(buyout_price) FROM prices";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Inserts row represented by this class to the database
     *
     * Constraint: medicament_id must exists otherwise this method won't work properly
     */
    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO prices (purchase_price, pharmacy_profit, " +
                    "insurance_company, patient, dph)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBigDecimal(1, purchasePrice);
            preparedStatement.setDouble(2, pharmacyProfit);
            preparedStatement.setBigDecimal(3, insurance_company);
            preparedStatement.setBigDecimal(4, patient);
            preparedStatement.setDouble(5, dph);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next())
                lastInsertedPriceID = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates row repsented by this class in database
     */
    @Override
    public void update() {
        try {
            String sql = "UPDATE prices SET purchase_price=?, pharmacy_profit=? " +
                    ",insurance_company=?, patient=?, dph=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setBigDecimal(1, purchasePrice);
            preparedStatement.setDouble(2, pharmacyProfit);
            preparedStatement.setBigDecimal(3, insurance_company);
            preparedStatement.setBigDecimal(4, patient);
            preparedStatement.setDouble(5, dph);
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
            String sql = "DELETE FROM prices WHERE price_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, priceID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
