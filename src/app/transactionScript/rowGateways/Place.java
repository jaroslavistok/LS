package app.transactionScript.rowGateways;


import app.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Place implements RowDataGateway {

    public int placeID;
    public String title;

    public static Place findById(int placeId){
        Place place = new Place();

        String query = "SELECT * FROM places WHERE place_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, placeId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            place.placeID = resultSet.getInt("place_id");
            place.title = resultSet.getString("title");

            resultSet.close();
            ConnectionManager.close();
            return place;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert() {
        try {
            String sql = "INSERT INTO places (title)" +
                    "VALUES (?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        try {
            String sql = "UPDATE places SET title=?" +
                    "WHERE place_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, placeID);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM places WHERE place_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, placeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
