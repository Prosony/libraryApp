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
         /********************************************************************************
         *                                  Library                                      *
         * ******************************************************************************/

            public ResultSet getDataLibraryFromDB(){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("select * " +
                            "from personal_library " +
                            "join account i2 on personal_library.id_account = i2.id_this_account;");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public void getDataLibraryFromDB(String libraryTitleFromTableListLibrary){
                libraryTitle = libraryTitleFromTableListLibrary;
                ResultSet rsQueryIdThisPersonalLibrary;
                connectionBD();
                try {
                    rsQueryIdThisPersonalLibrary = stmt.executeQuery("select * " +
                            "from personal_library WHERE library_title = '"+
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
         *                                  Books                                       *
         * *****************************************************************************/

            public ResultSet getFullDataAboutFromDB(String libraryTitle){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("select * from book join personal_library i1 " +
                            "on book.id_personal_library = i1.id_this_personal_library " +
                            "where library_title = '"+libraryTitle+"';");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public ResultSet getFullDataAboutBookForAddRelationships(String bookTitle){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("SELECT * FROM book where book_title = '"
                                                +bookTitle+"';");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public ResultSet getFullDataAboutBookWithRelationships(String bookTitle){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("SELECT *\n" +
                            "FROM \n" +
                            "(\n" +
                            "  (author INNER JOIN book_author ON author.id_this_author = book_author.id_author) \n" +
                            "    INNER JOIN book ON book.id_this_book = book_author.id_book\n" +
                            ")\twhere book.book_title = '"+bookTitle +"';");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
         /*********************************************************************************
          *                                       Author                                  *
          * ******************************************************************************/
            public int getDataAuthor(String fullNameAuthorBook){
                ResultSet resultSetIdAuthorBook;
                    int idAuthor;
                    connectionBD();
                    try {
                        resultSetIdAuthorBook = stmt.executeQuery(
                                "select * from author where Full_Name = '"+fullNameAuthorBook+"';");
                        while(resultSetIdAuthorBook.next()){
                            idAuthor = resultSetIdAuthorBook.getInt("id_this_author");
                            return idAuthor;
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                connectionClose();
                return 0;
            }
            public ResultSet getDataAuthor(int idAuthor){
                ResultSet resultSetAboutAuthor;

                connectionBD();
                try {
                    resultSetAboutAuthor = stmt.executeQuery(
                            "select * from author where id_this_author = '"+idAuthor+"';");
                    return resultSetAboutAuthor;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connectionClose();
                return null;
            }
            /*********************************************************************************
             *                               Relationships                                   *
             * ******************************************************************************/
            public ResultSet getDataAboutRelationships(String triger, int id){
                ResultSet resultSetDataAboutRelationships;

                try {
                    switch (triger){
                        case "book":
                            resultSetDataAboutRelationships = stmt.executeQuery(
                                    "SELECT * FROM book_author WHERE id_book = "+id+";");
                            return resultSetDataAboutRelationships;

                        case "author":
                            resultSetDataAboutRelationships = stmt.executeQuery(
                                    "SELECT * FROM book_author WHERE id_author = "+id+";");
                            return resultSetDataAboutRelationships;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }


/********************************************************************************
 *                      Methods for execute query UPDATE                         *
 * ******************************************************************************/
            /*********************************************************************************
             *                                  Library                                      *
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
             *                                  Books                                       *
             * *****************************************************************************/
            public void updataDataBook(String triger, String newTitleOrGenreBook, String oldTitleOrGenreBook){
                connectionBD();
                try {
                    switch (triger){
                        case "title":
                            stmt.executeUpdate("update book " +
                                    "set book.book_title = '"+newTitleOrGenreBook+"' " +
                                    "where book.book_title = '"+oldTitleOrGenreBook+"';");
                            break;
                        case "genre":
                            stmt.executeUpdate("update book " +
                                    "set book.genre = '"+newTitleOrGenreBook+"' " +
                                    "where book.genre = '"+oldTitleOrGenreBook+"';");
                            break;
                    }
                    stmt.executeUpdate("update book " +
                            "set book.book_title = '"+newTitleOrGenreBook+"' " +
                            "where book.book_title = '"+oldTitleOrGenreBook+"';");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connectionClose();
            }
            /*********************************************************************************
             *                               Relationships                                   *
             * ******************************************************************************/

            public void updateRelationships(int idRelationships, int idNewAuthor){
                connectionBD();

                try {
                    stdOut.println("_________updateRelationships_________");
                    stdOut.println("idNewAuthor: "+idNewAuthor);
                    stdOut.println("idRelationships: "+idRelationships);
                    stmt.executeUpdate("update book_author " +
                            "set id_author = '"+idNewAuthor+"' " +
                            "where book_author.id_book_author = '"+idRelationships+"';");
                    stdOut.println("relationships update");
                    stdOut.println("_____________________________________");
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

        ResultSet resultSetForFindIdThisBook;
        int idThisBook = 0;
        connectionBD();
        try {
            stdOut.println("idThisPersonalLibrary: "+idThisPersonalLibrary);
            stdOut.println(" bookTitle: " + bookTitle);
            stdOut.println(" idAuthor: " + idAuthor);
            stdOut.println(" genreBook: " + genreBook);

            stmt.execute("insert into book(id_personal_library, book_title, genre) " +
                        "values(" + idThisPersonalLibrary + ",'" + bookTitle +"', '" + genreBook + "');");


            resultSetForFindIdThisBook = getFullDataAboutBookForAddRelationships(bookTitle);
            while(resultSetForFindIdThisBook.next()){
                idThisBook = resultSetForFindIdThisBook.getInt("id_this_book");
            }
            stdOut.println("idThisBook: " + idThisBook);
            stdOut.println("idAuthor: " + idAuthor);
            stmt.execute("INSERT INTO book_author(id_book, id_author) VALUES ("
                    +idThisBook+","+idAuthor+");");
            stdOut.println("Book Added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
    }
/********************************************************************************
*                      Methods for execute query DELETE                         *
* *******************************************************************************************************
                         *                             GetLibrary Methods                               *
                         * *****************************************************************************/

                            public void deleteLibrary(String libraryTitleFromTableListSelected){
                                connectionBD();
                                try {
                                    stmt.execute("delete from personal_library where library_title = '"
                                                        +libraryTitleFromTableListSelected+"';");
                                    stdOut.println("Library was deleted");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                connectionClose();
                            }
                         /********************************************************************************
                         *                      SceneMain Methods (ContextMenu)                          *
                         * ******************************************************************************/
                            public void deleteBookFromPersonalLibrary(String bookTitle){
                                connectionBD();
                                int idBook = 0;
                                ResultSet aboutBook = getFullDataAboutBookForAddRelationships(bookTitle);
                                try {
                                    while(aboutBook.next()){
                                        idBook = aboutBook.getInt("id_this_book");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                stdOut.println("idBook for delete: " + idBook);
                                try {
                                    stmt.execute("delete from book_author where id_book = '"+idBook+"'");
                                    stdOut.println("Relationships was delete");
                                    /**
                                     * Create relationships |book <-> author|
                                     */
                                    stmt.execute("delete from book where book_title = '"
                                            +bookTitle+"';");
                                    stdOut.println("Book was deleted from Personal Library");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                connectionClose();
                            }
    /********************************************************************************
     *                              Other Methods                                   *
     * *************************************************************************************************
                         *                              Set Methods                                    *
                         * ****************************************************************************/
                        public void setIdAccount(int idAccount){
                            this.idAccount = idAccount;
                        }
                        /********************************************************************************
                         *                              Get Methods                                     *
                         * *****************************************************************************/
                        public int getIdThisPersonalLibrary(){return idThisPersonalLibrary;}
}
