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
/********************************************************************************
*                      MenuBar -> MenuItem Methods                              *
* ******************************************************************************/
    public void clickMenuItemGetLibrary(){
        String libraryTitle = sceneManager.showPanelLibrary();
        stdOut.println("libraryTitle: " + libraryTitle);
        setBooksTitleToTableFromLibrary(libraryTitle);
    }
                    private void setBooksTitleToTableFromLibrary(String libraryTitle) {
                        ResultSet resultSet;
                        if (libraryTitle != null) {
                            chestBooks.clear();
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
 *                              ContextMenu ActionEvents                        *                                                *
 * *****************************************************************************/
    public void contextMenuAddBook(ActionEvent actionEvent) {
        try {
            if (getIdThisPersonalLibrary() != 0) {
                sceneManager.showPanelAddBook();
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
        //TODO
    }

    public void contextMenuDeleteBook(ActionEvent actionEvent) {
        //TODO
    }

/********************************************************************************
*                              Other Metods                                     *
* ******************************************************************************/
    public void clickMenuItemExit() {System.exit(0);}
    public void buttonSystemExit(){
        System.exit(0);
    }
}



