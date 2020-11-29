package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_connection {
    public static Connection start_database() {
        String url = "jdbc:mysql://127.0.0.1:3306/project_dbms?user=root";
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
