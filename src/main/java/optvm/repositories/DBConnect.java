package optvm.repositories;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;

public final class DBConnect {

    public Connection conn;
    private Statement statement;
    public static DBConnect db;

    private DBConnect() {
        String url= "jdbc:mariadb://localhost:3306/";
        String dbName = "optvm";
        String driver = "com.mariadb.jdbc.Driver";
        String userName = "root";
        String password = null;

        try {
            this.conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    public static synchronized DBConnect getConnection() {
        if ( db == null )
            db = new DBConnect();
        return db;

    }

    public ResultSet query(String query) throws SQLException {
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;

    }
}
