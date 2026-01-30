
package com.rev.passwordmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "ANJAN";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }

        return con;
    }
 
}


