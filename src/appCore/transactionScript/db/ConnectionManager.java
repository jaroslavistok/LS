package appCore.transactionScript.db;
import org.postgresql.jdbc2.optional.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Thread safe implementation of simple database ConnectionManager
 */
public class ConnectionManager {
    private static ConnectionPool pool;
    private static ThreadLocal<Connection> connection = new ThreadLocal<>();

    /**
     * returns connection to database
     */
    public static Connection getConnection(){
        if (pool == null)
            initalizePool();

        if (connection.get() == null){
            try {
                connection.set(pool.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection.get();
    }

    /**
     * Close connection
     */
    public static void close(){
        if (connection.get() != null){
            try {
                connection.get().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.set(null);
        }
    }

    /**
     * Initialize ConnectionPool with database login information
     */
    public static void initalizePool(){
        pool = new ConnectionPool();
        pool.setDatabaseName("db1");
        pool.setUser("istok7");
        pool.setPassword("db1istok7");
        pool.setServerName("db.ii.fmph.uniba.sk");
        pool.setPortNumber(5432);
    }


}
