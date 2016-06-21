package appCore.transactionScript.rowGateways;


import appCore.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class State implements RowDataGateway {

    public int stateID;
    public String title;
    public int lastInsertedID;

    public static State findById(int placeId){
        State state = new State();

        String query = "SELECT * FROM states WHERE state_id=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setInt(1, placeId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            state.stateID = resultSet.getInt("state_id");
            state.title = resultSet.getString("title");

            resultSet.close();
            ConnectionManager.close();
            return state;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static State findByTitle(String placeTitle){
        State place = new State();

        String query = "SELECT * FROM states WHERE title=?";
        try {
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            statement.setString(1, placeTitle);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            place.stateID = resultSet.getInt("state_id");
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
            String sql = "INSERT INTO states (title)" +
                    "VALUES (?)";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                stateID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        try {
            String sql = "UPDATE states SET title=?" +
                    "WHERE state_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, stateID);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            String sql = "DELETE FROM states WHERE state_id=?";
            PreparedStatement preparedStatement =
                    ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, stateID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
