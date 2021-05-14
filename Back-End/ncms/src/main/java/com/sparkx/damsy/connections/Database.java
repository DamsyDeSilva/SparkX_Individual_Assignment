package com.sparkx.damsy.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final static String url = "jdbc:mysql://localhost:3306/ncms";
    private final static String user = "root";
    private final static String password = "root@123";
    private final static String driver = "com.mysql.jdbc.Driver";

    /**
     * Initiates Database connection
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection openConnection() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection is Successful to the database" + url);
        return connection;
    }
}
