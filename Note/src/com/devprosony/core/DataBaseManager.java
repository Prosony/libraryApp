package com.devprosony.core;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

import static com.devprosony.Main.stdOut;


/**
 * Created by Prosony on 23/03/17.
 */
abstract public class DataBaseManager {

    private static int idAccount;
    private static String libraryTitle;
    private static int idThisPersonalLibrary;

    public Statement stmt;
    private Connection connection;
    private Driver driver = new FabricMySQLDriver();

    public DataBaseManager() throws SQLException {}
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
                         *                      Modal Methods                                           *
                         * ******************************************************************************/
                            public int getIdAuthor(String authorBook){
                                ResultSet resultSetIdAuthorBook;
                                int idAuthor;
                                connectionBD();
                                try {
                                    resultSetIdAuthorBook = stmt.executeQuery(
                                            "select id_this_author from author where Full_Name = '"+authorBook+"';");
                                    while(resultSetIdAuthorBook.next()){
                                        idAuthor = resultSetIdAuthorBook.getInt("id_this_author");
                                        //stdOut.println("idAuthor: " + idAuthor);
                                        return idAuthor;
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                connectionClose();
                                return 0;
                            }
    /********************************************************************************
 *                      Methods for execute query UPDATE                         *
 * ******************************************************************************/
    public void renameLibraryTitle(String oldLibraryTitle, String newLibraryTitle){
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
/********************************************************************************
*                      Methods for execute query INSERT INTO                    *
* ******************************************************************************/

    public void createLibrary(String newLibraryTitle){
        connectionBD();
        try {
            stdOut.println("id: "+idAccount+" title: "+newLibraryTitle);
            stmt.execute("insert into personal_library(id_account,library_title) values("+idAccount+",'"+newLibraryTitle+"');");
            stdOut.println("Create new library: " + newLibraryTitle);
            connectionClose();
        } catch (SQLException e) {
            connectionClose();
            e.printStackTrace();
            }
    }
    public void addBookIntoPersonalLibrary(String  bookTitle, String genreBook, int idAuthor){
        /*
         * insert into books(id_personal_library, book_title, id_author, genre)
         * values(4,'Effective Java 2nd Edition', 5, 'Computers & Technology');
         */
        connectionBD();
        try {
            stdOut.println("idThisPersonalLibrary: "+idThisPersonalLibrary);
            stdOut.println(" bookTitle: "+bookTitle);
            stdOut.println(" idAuthor: "+idAuthor);
            stdOut.println(" genreBook: "+genreBook);

            stmt.execute("insert into books(id_personal_library, book_title, id_author, genre) " +
                        "values(" + idThisPersonalLibrary + ",'" + bookTitle + "', " + idAuthor + ", '" + genreBook + "');");
            stdOut.println("Book Added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
    }
/********************************************************************************
*                      Methods for execute query DELETE                         *
* ******************************************************************************/

    public void deleteLibrary(String libraryTitleFromTableListSelected){
        connectionBD();
        try {
            stmt.execute("delete from personal_library where library_title = '"
                                +libraryTitleFromTableListSelected+"';");
            stdOut.println("Library was deleted");
            connectionClose();
        } catch (SQLException e) {
            connectionClose();
            e.printStackTrace();
        }
    }
    /********************************************************************************
     *                              Other Methods                                    *
     * *************************************************************************************************
                         *                              Set Methods                                    *
                         * ******************************************************************************/
                        public void setIdAccount(int idAccount){
                            this.idAccount = idAccount;
                        }

                        public void setIdLibraryAndLibraryTitle(String libraryTitleFromTableListLibrary){
                            libraryTitle = libraryTitleFromTableListLibrary;
                            stdOut.println("libraryTitle from DataBaseManager: "+libraryTitle);
                            ResultSet rsQueryIdThisPersonalLibrary;
                            connectionBD();
                            try {
                                rsQueryIdThisPersonalLibrary = stmt.executeQuery("select id_this_personal_library from personal_library WHERE library_title = '"+
                                        libraryTitle+"'");
                                while(rsQueryIdThisPersonalLibrary.next()){
                                    idThisPersonalLibrary = rsQueryIdThisPersonalLibrary.getInt("id_this_personal_library");
                                    stdOut.println("libraryTitle from DataBaseManager: "+libraryTitle
                                                    +" idThisPersonalLibrary: "+idThisPersonalLibrary);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            connectionClose();
                        }
                        /********************************************************************************
                         *                              Get Methods                                     *
                         * *****************************************************************************/
                        public int getIdThisPersonalLibrary(){return idThisPersonalLibrary;}
}
