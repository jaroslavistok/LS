package appCore.transactionScript.transactionScripts;

import appCore.transactionScript.db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {
    public static int getNumberOfAllMedicaments(){
        try{
            String query = "SELECT count(*) FROM medicaments";
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


    public static int getNumberOfMedicamentsInLab(){
        try{
            String query = "SELECT count(*) FROM medicaments " +
                    "WHERE state_id=(SELECT state_id FROM states WHERE title='lab')";

            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumberOfMedicamentsInStore(){
        try{
            String query = "SELECT count(*) FROM medicaments " +
                    "WHERE state_id=(SELECT state_id FROM states WHERE title='na_sklade')";

            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumberOfSoldMedicaments(){
        try{
            String query = "SELECT count(*) FROM medicaments " +
                    "WHERE state_id=(SELECT state_id FROM states WHERE title='predane')";

            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumberOfExpiredMedicaments(){
        try{
            String query = "SELECT count(*) FROM medicaments INNER JOIN medicament_information ON medicaments.medicament_information_id = medicament_information.medicament_information_id WHERE medicament_information.expiration > now()";
            PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static double getTotalMoneyValueOfAllMedicaments(){
        try{
            String sql = "SELECT sum(selling_price) FROM prices";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getDouble(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static double getMoneyValueOfAllMedicamentsInStore(){
        try{
            String sql = "SELECT sum(selling_price) FROM prices INNER JOIN medicaments ON prices.price_id=medicaments.price_id INNER JOIN states ON medicaments.state_id=states.state_id WHERE states.title='predane'";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getDouble(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;


    }

    public static double getMoneyValueOfAllSoldMedicamentsLastMonth(){
        try{
            String sql = "SELECT sum(selling_price) FROM prices INNER JOIN medicaments ON prices.price_id=medicaments.price_id INNER JOIN states ON medicaments.state_id=states.state_id WHERE states.title='predane'";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getDouble(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static double getMoneyValueOfAllSoldMedicamentsLastYear(){

        try{
            String sql = "SELECT sum(selling_price) FROM prices INNER JOIN medicaments ON prices.price_id=medicaments.price_id INNER JOIN states ON medicaments.state_id=states.state_id WHERE states.title='predane'";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getDouble(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


    public static int getNumberOfUnRetaxedRecipes(){
        try{
            String sql = "SELECT count(recipe_id) FROM recipes WHERE recipe_batch_id IS NOT NULL";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumberOfRetaxedrecipes(){
        try{
            String sql = "SELECT count(recipe_id) FROM recipes WHERE recipe_batch_id IS NULL";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumberOfAllRecipes(){
        try{
            String sql = "SELECT count(recipe_id) FROM recipes";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


}
