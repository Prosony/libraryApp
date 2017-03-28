package com.devprosony.core.controllers;

import com.devprosony.Main;
import com.devprosony.core.ConnectionToBD;
import com.devprosony.core.controllers.model.TableDataView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenMain extends ConnectionToBD  implements Initializable {

    @FXML MenuBar menuBar;
    @FXML Button buttonSystemExit;
    @FXML Button buttonFullScreen;
    @FXML private TableView<TableDataView> tableBookTitle;
    @FXML private TableColumn<TableDataView, String> bookTitle;

    private ObservableList<TableDataView> bookList = FXCollections.observableArrayList();

    private Stage primaryStage;
    private ResultSet rs;
    private int count = 0;
    private Main mainApp;
    public ScreenMain() throws SQLException {
        stdOut.println("Hey from Screen2Controller controller");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        bookTitle.setCellValueFactory(new PropertyValueFactory<TableDataView, String>("Books"));
        tableBookTitle.setItems(bookList);

    }
    private void initData() {
        bookList.add(new TableDataView("Book1"));
        bookList.add(new TableDataView("Book2"));
        bookList.add(new TableDataView("Book3"));
        bookList.add(new TableDataView("Book4"));
    }
    public void clickMenuItemGetLibrary(){
        //TODO
        String libraryTitle = sceneManager.showTableListLibrary();
        stdOut.println("libraryTitle: " + libraryTitle);
        setTableDataFromLibrary(libraryTitle);
    }

    public void clickMenuItemExit() {

        //connectionClose();
        System.exit(0);
    }
    public void buttonSystemExit(){
        System.exit(0);
    }

    private void setTableDataFromLibrary(String libraryTitle){
        if(libraryTitle != "null"){
            String url = "select books.book_title from books join personal_library i1 on books.id_personal_library = i1.id_this_personal_library where id_personal_library = 4;";
                    bookList.clear();
                    stdOut.println("Hey from setTableDataList");

                    connectionBD();
                    try {
                        rs = stmt.executeQuery(url);
                        while(rs.next()) {

                            String nameBook = rs.getString("book_title");
                            stdOut.println("Name: " + nameBook);
                            bookList.add(new TableDataView(nameBook));
                        }
                        connectionClose();
                        bookList.forEach(System.out::println);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tableBookTitle.setItems(bookList);
                    //stdOut.println("not null");
        }else{
            stdOut.println("null");
        }
    }

//    class ThreadSetTableBookTitle extends Thread{
//        private String libraryTitle;
//        private String url;
//        public ThreadSetTableBookTitle(String libraryTitle){
//            this.libraryTitle = libraryTitle;
//        }
//
//        @Override
//        public void run(){
//            url = "select books.book_title, books.author, books.genre from books " +
//                    "join personal_library i1 on books.id_library = i1.id_this_personal_library " +
//                    "where library_title = '"+libraryTitle+"';";
//            bookList.clear();
//            stdOut.println("Hey from setTableDataList");
//
//                connectionBD();
//            try {
//                rs = stmt.executeQuery(url);
//                while(rs.next()) {
//
//                    String nameBook = rs.getString("Book_title");
//                    stdOut.println("Name: " + nameBook);
//                    bookList.add(new TableDataView(nameBook));
//                }
//                connectionClose();
//                bookList.forEach(System.out::println);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            tableBookTitle.setItems(bookList);
//        }
//    }





}



