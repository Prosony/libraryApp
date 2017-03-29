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
/********************************************************************************
*                   Methods for connect/disconnect DataBase                     *
* ******************************************************************************/

    public void connectionBD() {
        final String URL = "jdbc:mysql://localhost:3306/llibrary?autoReconnect=true&useSSL=false";
        final String USERNAME = "root";
        final String PASSWORD = "Pl0Ok9Ij8";
        try {
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = connection.createStatement();
            if (!(connection.isClosed())) {
                System.out.println("Connection to BD is installed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectionClose(){
        try {
            connection.close();
            if (connection.isClosed()) {
                System.out.println("Connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
 /********************************************************************************
 *                      Methods for execute query SELECT                         *
 * ******************************************************************************/

    public ResultSet getLibraryTitleFromDB(){
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select personal_library.library_title " +
                    "from personal_library " +
                    "join account i2 on personal_library.id_account = i2.id_this_account;");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getBooksFromDB(String libraryTitle){
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select books.book_title from books join personal_library i1 " +
                    "on books.id_personal_library = i1.id_this_personal_library " +
                    "where library_title = '"+libraryTitle+"';");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
/********************************************************************************
 *                      Methods for execute query UPDATE                         *
 * ******************************************************************************/
    public void renameLibraryTitle(String oldLibraryTitle, String newLibraryTitle){
        ResultSet rs;
        connectionBD();
        try {
            stmt.executeUpdate("update personal_library " +
                    "set library_title = '"+newLibraryTitle+"' " +
                    "where library_title = '"+oldLibraryTitle+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
    }

}
