package com.devprosony.core;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;


/**
 * Created by Prosony on 23/03/17.
 */
abstract public class ConnectionToBD {

    public Statement stmt;
    private Connection connection;
    private Driver driver = new FabricMySQLDriver();


    public ConnectionToBD() throws SQLException {
    }

    public void connectionBD() {
        final String URL = "jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false";
        final String USERNAME = "root";
        final String PASSWORD = "root";
        try {
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = connection.createStatement();
            if (!(connection.isClosed())) {
                System.out.printf("Connection to BD is installed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectionClose(){
        try {
            connection.close();
            if (connection.isClosed()) {
                System.out.printf("Connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
