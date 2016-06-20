package appCore.transactionScript.rowGateways;


import appCore.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class State implements RowDataGateway {

    public int placeID;
    public String title;

    public static State findById(int placeId){
        State place = new State();

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

    public static State findByTitle(String placeTitle){
        State place = new State();

        String query = "SELECT * FROM places WHERE title=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, placeTitle);
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
