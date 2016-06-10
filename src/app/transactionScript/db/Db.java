package app.transactionScript.db;
import org.postgresql.jdbc2.optional.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class Db {
    static ConnectionPool pool;
    static ThreadLocal<Connection> connection = new ThreadLocal<>();

    static Connection getConnection(){
        if (connection.get() == null){
            try {
                connection.set(pool.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection.get();
    }

    static void close(){
        if (connection.get() != null){
            try {
                connection.get().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.set(null);
        }
    }

    public static void initalizePool(){
        pool = new ConnectionPool();
        pool.setDatabaseName("db1");
        pool.setUser("istok7");
        pool.setPassword("db1istok7");
        pool.setServerName("db.ii.fmph.uniba.sk");
        pool.setPortNumber(5432);
    }




}
