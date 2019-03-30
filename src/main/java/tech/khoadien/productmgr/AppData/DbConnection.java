package tech.khoadien.productmgr.AppData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            String dbUrl = "jdbc:mysql://mysql7003.site4now.net:3306/db_a41854_promgr";
            connection = DriverManager.getConnection(dbUrl, "a41854_promgr", "");
        } catch (SQLException e) {
            System.err.println(e);
        }

        return connection;
    }
}
