package com.devprosony.core.controllers;

import com.devprosony.Main;
import com.devprosony.core.DataBaseManager;
import com.devprosony.core.controllers.modal.controller.main.ScreenPartTabMain;
import com.devprosony.core.controllers.model.TableDataView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sun.plugin.javascript.navig.Anchor;

import javax.xml.transform.Result;
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
    TabPane tabBookPane;
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
        sceneManager.showPanelLibrary();
        tableUpdate();
    }
    public void clickMenuItemLogOut() {
        idAccount = 0;
        idThisPersonalLibrary = 0;
        chestBooks.clear();
        sceneManager.switchScene("SceneChoose");
    }
    public void clickMenuItemSearchPane() {
        sceneManager.showPanelSearch();
    }

    public void clickMenuItemOption() {
        sceneManager.showPanelOptions();
    }

    /********************************************************************************
 *                              ContextMenu ActionEvents                        *                                                *
 * *****************************************************************************/
    public void contextMenuAddBook(ActionEvent actionEvent) {
        try {
            if (getIdThisPersonalLibrary() != 0) {
                sceneManager.showPanelAddBook();
                //**     New Thread for input books in table from library, when PanelAddBook will be close*/
                tableUpdate();
            }else{
                stdOut.println("Before select library, then add books (:");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void contextMenuOpenBook() {

        String titleBook = String.valueOf(tableBooks.getSelectionModel().getSelectedItem());
        String bookFullNameAuthor = null;
        String about = null;

        if(idThisPersonalLibrary != 0) {
            if (titleBook != "null" && titleBook != null && !titleBook.isEmpty()) {
                connectionBD();
                ResultSet resultSet = getFullDataAboutBookWithRelationships(titleBook);
                try {
                    while (resultSet.next()) {
                        bookFullNameAuthor = resultSet.getString("full_name");
                        about = resultSet.getString("about");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connectionClose();

                addTab(titleBook, bookFullNameAuthor, about);
            } else {
                sceneManager.showNotifications("Error", "Before select book!");
            }
        }else{
            sceneManager.showNotifications("Error", "Before open Library!");
        }
    }

    public void contextMenuEditBook() {

        int idRelationships = 0;
        int oldIdBook = 0;
        int oldIdAuthor = 0;
        String oldTitleBook = String.valueOf(tableBooks.getSelectionModel().getSelectedItem());
        String oldGenreBook = null;
        String oldFullNameAuthor = null;
        String oldAboutBook = null;
        ResultSet dataAboutBook;

        connectionBD();
        dataAboutBook = getFullDataAboutBookWithRelationships(oldTitleBook);
        try {
            while (dataAboutBook.next()) {
                if(dataAboutBook.getInt("id_this_book") != 0){
                    idRelationships = dataAboutBook.getInt("id_book_author");
                    oldIdAuthor = dataAboutBook.getInt("id_this_author");
                    oldIdBook = dataAboutBook.getInt("id_this_book");
                    oldGenreBook = dataAboutBook.getString("genre");
                    oldFullNameAuthor = dataAboutBook.getString("full_name");
                    oldAboutBook = dataAboutBook.getString("about");
                }
            }
            connectionClose();
            sceneManager.showPanelEditBook(idRelationships, oldIdBook, oldIdAuthor, oldTitleBook, oldGenreBook,
                    oldFullNameAuthor, oldAboutBook);
            tableUpdate();
        } catch (SQLException e) {
            connectionClose();
            e.printStackTrace();
        } catch (IOException e) {
            connectionClose();
            e.printStackTrace();
        }
    }

    public void contextMenuDeleteBook() {
        String bookTitle = String.valueOf(tableBooks.getSelectionModel().getSelectedItem());
        if (getIdThisPersonalLibrary() != 0) {
            if (bookTitle != "null" && bookTitle != null && !bookTitle.isEmpty()) {
                stdOut.println("bookTitle: " + bookTitle);
                deleteBookFromPersonalLibrary(bookTitle);
                stdOut.println("Thread book open");
                tableUpdate();
            }else{
                sceneManager.showNotifications("Error","Before select Book!");
            }
        }else{
                sceneManager.showNotifications("Error","Before select Library!");
        }
    }
    /********************************************************************************
     *      The thread takes bookTitle from the DB and fills the Table              *
     *******************************************************************************/
    public void tableUpdate(){
        ResultSet resultSet;
        chestBooks.clear();
        connectionBD();
        resultSet = getFullDataAboutBookFromSelectionLibraryFromDB();

        if (resultSet != null) {
            try {
                stdOut.println("search book");
                while (resultSet.next()) {
                    String nameBook = resultSet.getString("book_title");
                    stdOut.println("Name: " + nameBook);
                    chestBooks.add(new TableDataView(nameBook));
                }
                tableBooks.setItems(chestBooks);
                connectionClose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            stdOut.println("tableupdate -> resultSet == null");
        }
    }
/********************************************************************************
*                                   TabPane                                     *
* ******************************************************************************/

    @FXML
    private void addTab(String titleBook, String bookFullNameAuthor, String about){
//        int numTabs = tabBookPane.getTabs().size();
        Tab tab = new Tab(titleBook);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("core/controllers/view/main/tab/PanelBookTab.fxml"));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tab.setContent(page);
        ScreenPartTabMain screenPartTabMain = loader.getController();
        screenPartTabMain.setLabelText(titleBook, bookFullNameAuthor, about);
        tabBookPane.getTabs().add(tab);

}
    /********************************************************************************
     *                                   ContextMenuTabPane                         *
     * *****************************************************************************/
        public void tabContextMenuCloseTab(ActionEvent actionEvent) {
            tabBookPane.getTabs().remove( tabBookPane.getSelectionModel().getSelectedIndex() );
        }

/********************************************************************************
*                              Other Metods                                     *
* ******************************************************************************/
    public void clickMenuItemExit() {System.exit(0);}
    public void buttonSystemExit(){
        System.exit(0);
    }
}



