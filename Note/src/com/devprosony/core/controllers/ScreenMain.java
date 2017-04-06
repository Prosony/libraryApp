package com.devprosony.core.controllers;

import com.devprosony.Main;
import com.devprosony.core.DataBaseManager;
import com.devprosony.core.controllers.model.TableDataView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenMain extends DataBaseManager {

    private ObservableList<TableDataView> chestBooks = FXCollections.observableArrayList();
    private Main mainApp;
    private String libraryTitle;
    @FXML private TableView<TableDataView> tableBooks;
    @FXML private TableColumn<TableDataView, String> booksTitle;

    @FXML
    private void initialize() {
        chestBooks.add(new TableDataView("Select"));
        chestBooks.add(new TableDataView(" Library"));
        booksTitle.setCellValueFactory(new PropertyValueFactory<TableDataView, String>("BookTitle"));
        tableBooks.setItems(chestBooks);
    }
    public ScreenMain() throws SQLException {}

/********************************************************************************
*                      MenuBar -> MenuItem Methods                              *
* ******************************************************************************/
    public void clickMenuItemGetLibrary(){
        libraryTitle = sceneManager.showPanelLibrary();
        stdOut.println("libraryTitle: " + libraryTitle);
        ThreadSetTableListBooks threadSetTableListBooks = new ThreadSetTableListBooks();
        threadSetTableListBooks.start();
    }
/********************************************************************************
 *                              ContextMenu ActionEvents                        *                                                *
 * *****************************************************************************/
    public void contextMenuAddBook(ActionEvent actionEvent) {
        try {
            if (getIdThisPersonalLibrary() != 0) {
                sceneManager.showPanelAddBook();
                //**     New Thread for input books in table from library, when PanelAddBook will be close*/
                stdOut.println("Thread book open");
                ThreadSetTableListBooks threadSetTableListBooks = new ThreadSetTableListBooks();
                threadSetTableListBooks.start();
            }else{
                stdOut.println("Before select library, then add books (:");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void contextMenuOpenBook(ActionEvent actionEvent) {
        //TODO
    }

    public void contextMenuEditBook(ActionEvent actionEvent) {
        String oldTitleBook = String.valueOf(tableBooks.getSelectionModel().getSelectedItem());
        ResultSet dataAboutBook;
        connectionBD();
        dataAboutBook = getFullDataAboutBook(oldTitleBook);
        String oldGenreBook = null;
        int oldIdAuthor = 0;
        try {
            while (dataAboutBook.next()) {
                if(dataAboutBook.getInt("id_this_book") != 0){
                    oldGenreBook = dataAboutBook.getString("genre");
                    //oldIdAuthor = dataAboutBook.getInt("id_author");
                }
            }
            stdOut.println("title: " + oldTitleBook);
            stdOut.println("genre: " + oldGenreBook);
            connectionClose();
            sceneManager.showPanelEditBook(oldTitleBook, oldGenreBook);
        } catch (SQLException e) {
            connectionClose();
            e.printStackTrace();
        } catch (IOException e) {
            connectionClose();
            e.printStackTrace();
        }
        //getDataAuthor(oldIdAuthor);c
    }

    public void contextMenuDeleteBook(ActionEvent actionEvent) {
        if (getIdThisPersonalLibrary() != 0) {
                String bookTitle = String.valueOf(tableBooks.getSelectionModel().getSelectedItem());
                stdOut.println("bookTitle: " + bookTitle);
                deleteBookFromPersonalLibrary(bookTitle);
                stdOut.println("Thread book open");
                ThreadSetTableListBooks threadSetTableListBooks = new ThreadSetTableListBooks();
                threadSetTableListBooks.start();
        }else{
                stdOut.println("Before select library, then add books (:");
        }

    }

    /********************************************************************************
     *      The thread takes bookTitle from the DB and fills the Table              *
     *******************************************************************************/
    class ThreadSetTableListBooks extends Thread{
        @Override
        public void run(){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ResultSet resultSet;
                if (libraryTitle != null) {
                    chestBooks.clear();
                    connectionBD();
                    resultSet = getFullDataAboutFromDB(libraryTitle);
                    try {
                        while (resultSet.next()) {
                            String nameBook = resultSet.getString("book_title");
                            stdOut.println("Name: " + nameBook);
                            chestBooks.add(new TableDataView(nameBook));
                            tableBooks.setItems(chestBooks);
                        }
                        connectionClose();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    stdOut.println("null");
                }
        }
    }
/********************************************************************************
*                              Other Metods                                     *
* ******************************************************************************/
    public void clickMenuItemExit() {System.exit(0);}
    public void buttonSystemExit(){
        System.exit(0);
    }
}



