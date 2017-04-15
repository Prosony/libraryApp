package com.devprosony.core;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;


/**
 * Created by Prosony on 23/03/17.
 */
abstract public class DataBaseManager {

    protected static int idAccount;
    protected static int idThisPersonalLibrary;

    protected Statement stmt;
    private Connection connection;
    private Driver driver = new FabricMySQLDriver();

    protected DataBaseManager() throws SQLException {}
/********************************************************************************
*                   Methods for connect/disconnect DataBase                     *
* ******************************************************************************/

    protected void connectionBD() {
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

    protected void connectionClose(){
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

         protected ResultSet getDataLibraryFromDB(){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("select * " +
                            "from personal_library " +
                            "join account i2 on personal_library.id_account = i2.id_this_account " +
                            "where id_this_account = "+idAccount+";");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void getDataLibraryFromDB(String libraryTitleFromTableListLibrary){
                String libraryTitle = libraryTitleFromTableListLibrary;
                ResultSet rsQueryIdThisPersonalLibrary;
                connectionBD();
                try {
                    rsQueryIdThisPersonalLibrary = stmt.executeQuery(
                            "select * from personal_library \n" +
                                    "join account i2 on personal_library.id_account = i2.id_this_account \n" +
                                    "where i2.id_this_account = "+idAccount+" and personal_library.library_title = '"
                                    +libraryTitleFromTableListLibrary+"';");
                    while(rsQueryIdThisPersonalLibrary.next()){
                        idThisPersonalLibrary = rsQueryIdThisPersonalLibrary.getInt("id_this_personal_library");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connectionClose();
            }

        /********************************************************************************
         *                                  Books                                       *
         * *****************************************************************************/

            protected ResultSet getFullDataAboutBookFromSelectionLibraryFromDB(){
                ResultSet rs;
                if(idThisPersonalLibrary != 0){
                    try {
                        rs = stmt.executeQuery("select * from book join personal_library i1 " +
                                "on book.id_personal_library = i1.id_this_personal_library " +
                                "where book.id_personal_library = '"+idThisPersonalLibrary+"';");
                        return rs;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    stdOut.println("getFullDataAboutBookFromSelectionLibraryFromDB()->idThisPersonalLibrary: " + idThisPersonalLibrary);
                }
                return null;
            }
            private ResultSet getFullDataAboutBookForAddRelationships(String bookTitle){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("SELECT * FROM book where book_title = '"
                                                +bookTitle+"' and id_personal_library = "+idThisPersonalLibrary+";");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected ResultSet getFullDataAboutBookWithRelationships(String bookTitle){
                ResultSet rs;
                try {
                    rs = stmt.executeQuery("SELECT *\n" +
                            "FROM \n" +
                            "(\n" +
                            "  (author INNER JOIN book_author ON author.id_this_author = book_author.id_author) \n" +
                            "    INNER JOIN book ON book.id_this_book = book_author.id_book\n" +
                            ")\twhere book.book_title = '"+bookTitle +"' and id_personal_library = "+idThisPersonalLibrary+";");
                    return rs;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected  ResultSet getFullDataBookForSearchTable(boolean titleTriger, boolean genreTriger,
                                                               boolean authorTriger, String title,
                                                                String genre, String author){
                ResultSet resultSet = null;
                boolean first = true;
                stdOut.println("___________________________________________");
                String query = "SELECT * FROM author " +
                        "JOIN book_author ON author.id_this_author = book_author.id_author " +
                        "JOIN book ON book.id_this_book = book_author.id_book ";
                if(titleTriger){
                    if (first){
                        stdOut.println("title: "+titleTriger);
                        first = false;
                        query = query + "where book.book_title = '"+title+"' ";
                    }
                }
                if(genreTriger){
                    if (first){
                        first = false;
                        query = query + "where book.genre = '"+genre+"' ";
                    }else{
                        query = query + "and book.genre = '"+genre+"' ";
                    }
                    stdOut.println("genre: "+genreTriger);
                }
                if (authorTriger){
                    if (first){
                        first = false;
                        query = query + "where full_name = '"+author+"'";
                    }else{
                        query = query + "and full_name = '"+author+"'";
                    }
                    stdOut.println("author: "+authorTriger);
                }
                query = query + ";";
                try {
                    resultSet = stmt.executeQuery(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                stdOut.println("___________________________________________");
                return resultSet;
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
                            connectionClose();
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
                            "where library_title = '"+oldLibraryTitle+"' and id_account = "+idAccount+";");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connectionClose();
            }
            /********************************************************************************
             *                                  Books                                       *
             * *****************************************************************************/
            public void upDataBook(String triger, int idBook, String newTitleOrGenreBookOrAbout){
                connectionBD();
                try {
                    switch (triger){
                        case "title":

                            stmt.executeUpdate("update book " +
                                    "set book.book_title = '"+newTitleOrGenreBookOrAbout+"' " +
                                    "where book.id_this_book = "+idBook+";");
                            break;
                        case "genre":
                            stmt.executeUpdate("update book " +
                                    "set book.genre = '"+newTitleOrGenreBookOrAbout+"' " +
                                    "where book.id_this_book = "+idBook+";");
                            break;
                        case "about":
                            stdOut.println("executeUpdate");
                            PreparedStatement aboutUpdate = connection.prepareStatement(
                                    "update book set book.about = ? where book.id_this_book = ?;");
                            aboutUpdate.setString(1, newTitleOrGenreBookOrAbout);
                            aboutUpdate.setInt(2, idBook);
                            aboutUpdate.executeUpdate();
                            break;
                    }
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
            stmt.execute("insert into personal_library(id_account,library_title) values("
                            +idAccount+",'"+newLibraryTitle+"');");
            stdOut.println("Create new library: " + newLibraryTitle);

            sceneManager.showNotifications("success","Library: "+newLibraryTitle+" is creat");
        } catch (SQLException e) {
            e.printStackTrace();
            }
        connectionClose();
    }
    public void addBookIntoPersonalLibrary(String bookTitle, String genreBook, int idAuthor, String bookAbout){
        /*
         * insert into books(id_personal_library, book_title, id_author, genre)
         * values(4,'Effective Java 2nd Edition', 5, 'Computers & Technology');
         */
        ResultSet resultSetForFindIdThisBook;
        int idThisBook = 0;
        connectionBD();
        try {
            stdOut.println("idThisPersonalLibrary: " + idThisPersonalLibrary);
            stdOut.println(" bookTitle: " + bookTitle);
            stdOut.println(" idAuthor: " + idAuthor);
            stdOut.println(" genreBook: " + genreBook);
            stdOut.println("_______About_book_______");
            stdOut.println(bookAbout);
            stdOut.println("________________________");

            PreparedStatement addBookStmt = connection.prepareStatement(
                    "insert into book(id_personal_library, book_title, genre, about) " +
                            "values( ?, ?, ?, ?);");
            addBookStmt.setInt(1, idThisPersonalLibrary);
            addBookStmt.setString(2, bookTitle);
            addBookStmt.setString(3, genreBook);
            addBookStmt.setString(4, bookAbout);
            addBookStmt.execute();

            resultSetForFindIdThisBook = getFullDataAboutBookForAddRelationships(bookTitle);
            while(resultSetForFindIdThisBook.next()){
                idThisBook = resultSetForFindIdThisBook.getInt("id_this_book");
            }
            stdOut.println("idThisBook: " + idThisBook);
            stdOut.println("idAuthor: " + idAuthor);
            stmt.execute("INSERT INTO book_author(id_book, id_author) VALUES ("
                    +idThisBook+","+idAuthor+");");
            sceneManager.showNotifications("success","Book Added");
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

                                protected void deleteLibrary(String libraryTitleFromTableListSelected){
                                connectionBD();
                                try {
                                    stmt.execute("delete from personal_library where library_title = '"
                                                        +libraryTitleFromTableListSelected+"';");
                                    sceneManager.showNotifications("success","Library was deleted");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                connectionClose();
                            }
                         /********************************************************************************
                         *                      SceneMain Methods (ContextMenu)                          *
                         * ******************************************************************************/
                         protected void deleteBookFromPersonalLibrary(String bookTitle){
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
                                    stmt.execute("DELETE FROM book WHERE id_this_book = "+idBook+";");
                                    sceneManager.showNotifications("success","Book was deleted from Personal Library(");
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
                        protected void setIdAccount(int idAccount){
                            DataBaseManager.idAccount = idAccount;
                        }
                        /********************************************************************************
                         *                              Get Methods                                     *
                         * *****************************************************************************/
                        protected int getIdThisPersonalLibrary(){return idThisPersonalLibrary;}
}
