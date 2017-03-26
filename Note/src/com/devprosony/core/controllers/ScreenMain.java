package com.devprosony.core.controllers;

import com.devprosony.core.ConnectionToBD;
import com.devprosony.core.controllers.model.TableDataView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lib.StdOut;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenMain extends ConnectionToBD  {


    @FXML
    MenuBar menuBar;
    @FXML
    Button buttonSystemExit;
    @FXML
    Button buttonFullScreen;
    @FXML
    public TableView<TableDataView> tableData;
    @FXML
    public TableColumn<TableDataView, String> nameBook;
    private Stage primaryStage;


    private ObservableList<TableDataView> tableDataList = FXCollections.observableArrayList();
    static StdOut stdOut = new StdOut();
    private ResultSet rs;
    private int count = 0;

    public ScreenMain() throws SQLException {
        stdOut.println("Hey from Screen2Controller controller");
    }
    @FXML
    private void initialize() {
        nameBook.setCellValueFactory(new PropertyValueFactory<TableDataView, String>("nameBook"));
    }

    public void setTableDataList(){
        tableDataList.clear();
        stdOut.println("Hey from setTableDataList");
        try {
            connectionBD();
            rs = stmt.executeQuery("select name_library from personal_library;");
            while(rs.next()) {
                String nameBook = rs.getString("name_library");
                count = tableDataList.toString().length();
                stdOut.println("Count "+ count);

                        stdOut.println("Name: " + nameBook);
                        tableDataList.add(new TableDataView(nameBook));
                        stdOut.println("tableList: " + tableDataList);

            }
            tableDataList.forEach(System.out::println);
            tableData.setItems(tableDataList);
            connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void clickMenuItemGetLibrary(){
        //TODO
        sceneManager.showTableListLibrary();
    }

    public void clickMenuItemExit() {
        //connectionClose();
        System.exit(0);
    }
    public void buttonSystemExit(){
        System.exit(0);
    }
}

