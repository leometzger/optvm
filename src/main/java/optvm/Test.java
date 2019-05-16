package optvm;

import java.sql.*;

public class Test {

    public static void main( String[] args ) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/",
                "root",
                "test"
        )) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT 'Hello World!'")) {
                    rs.first();
                    System.out.println(rs.getString(1)); //result is "Hello World!"
                }
            }
        }
    }
}
