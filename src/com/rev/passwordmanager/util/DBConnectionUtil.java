package com.rev.passwordmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionUtil {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "ANJAN";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
