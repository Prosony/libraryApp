package com.devprosony.core.controllers;

import com.devprosony.Main;
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

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenMain extends ConnectionToBD {

    private ObservableList<TableDataView> chestBooks = FXCollections.observableArrayList();
    private Main mainApp;

    @FXML MenuBar menuBar;
    @FXML Button buttonSystemExit;
    @FXML private TableColumn<TableDataView, String> booksTitle;
    @FXML private TableView<TableDataView> tableBooks;

    @FXML
    private void initialize() {
        initData();
        booksTitle.setCellValueFactory(new PropertyValueFactory<TableDataView, String>("BookTitle"));
        tableBooks.setItems(chestBooks);
    }
    public ScreenMain() throws SQLException {
        stdOut.println("Hey from Screen2Controller controller");
    }

    private void initData() {
        chestBooks.add(new TableDataView("Select"));
        chestBooks.add(new TableDataView(" Library"));
    }
    public void clickMenuItemGetLibrary(){
        String libraryTitle = sceneManager.showPanelLibrary();
        stdOut.println("libraryTitle: " + libraryTitle);
        setTableDataFromLibrary(libraryTitle);
    }

    private void setTableDataFromLibrary(String libraryTitle) {
        ResultSet resultSet;
        chestBooks.clear();
        if (libraryTitle != "null") {
                    connectionBD();
                    resultSet = getBooksFromDB(libraryTitle);
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
/********************************************************************************
*                              Other Metods                                     *
* ******************************************************************************/
    public void clickMenuItemExit() {System.exit(0);}
    public void buttonSystemExit(){
        System.exit(0);
    }


}



