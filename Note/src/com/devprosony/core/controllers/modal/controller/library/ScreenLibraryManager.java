package com.devprosony.core.controllers.modal.controller.library;

import com.devprosony.core.DataBaseManager;
import com.devprosony.core.controllers.model.ViewListLibrary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenLibraryManager extends DataBaseManager {

    @FXML public TableView<ViewListLibrary> tableListLibrary;
    @FXML public TableColumn<ViewListLibrary, String> nameLibrary;
    private ObservableList<ViewListLibrary> libraryList = FXCollections.observableArrayList();

        private Stage dialogStage;
        private String libraryTitle;
        public ScreenLibraryManager() throws SQLException {}


        @FXML
        private void initialize() {
            nameLibrary.setCellValueFactory(new PropertyValueFactory<ViewListLibrary, String>("nameLibrary"));
            stdOut.println("thread open ");
            Thread threadSetTableListLibrary = new ThreadSetTableListLibrary();
            threadSetTableListLibrary.start();
        }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /********************************************************************************
     *                              Button ActionEvents                             *
     * *****************************************************************************/
    public void openLibrary(){
        stdOut.println("Open");
        /** selection title into libraryTitle*/
        libraryTitle = String.valueOf(tableListLibrary.getSelectionModel().getSelectedItem());
        /**This method input libraryTitle into DataBaseManager.libraryTitle and get id_this_library from DB*/
        setIdLibraryAndLibraryTitle(libraryTitle);
        stdOut.println("\ntext: " + libraryTitle);
        dialogStage.close();
    }

    public void pressCancel() {
            stdOut.println("Press Cancel");
            dialogStage.close();
        }

    /********************************************************************************
     *                              ContextMenu ActionEvents                        *                                                *
     * *****************************************************************************/
    public void contextMenuCreateLibrary(ActionEvent actionEvent) {
        try {
            sceneManager.showPanelCreateLibraryStage();
            /**
             * Create Thread for Update table Library
             * */
            Thread threadSetTableListLibrary = new ThreadSetTableListLibrary();
            threadSetTableListLibrary.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void contextMenuRenameLibrary(ActionEvent actionEvent) {
        String libraryTitleFromTableListSelected = String.valueOf(tableListLibrary.getSelectionModel().getSelectedItem());

        stdOut.println("libraryTitle: " + libraryTitleFromTableListSelected);
        try {

            boolean libraryWasChange = sceneManager.showPanelRenameLibrary(libraryTitleFromTableListSelected);
            /**
             * If library title was change, that libraryWasChange = true,
             * and new thread will be created and he update TableListTitle in panel
             */
            if(libraryWasChange){
                /******************************************
                 * Update dataset in table with new Thread*
                 *****************************************/
                Thread threadSetTableListLibrary = new ThreadSetTableListLibrary();
                threadSetTableListLibrary.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contextMenuDeleteLibrary(ActionEvent actionEvent) {
        String libraryTitleFromTableListSelected = String.valueOf(tableListLibrary.getSelectionModel().getSelectedItem());
        stdOut.println("Delete library: " + libraryTitleFromTableListSelected);
        deleteLibrary(libraryTitleFromTableListSelected);
        Thread threadSetTableListLibrary = new ThreadSetTableListLibrary();
        threadSetTableListLibrary.start();
    }

    /********************************************************************************
     *      The thread takes libraryTitle from the DB and fills the Table           *
     *******************************************************************************/
    class ThreadSetTableListLibrary extends Thread{
            @Override
            public void run(){
                try {
                    libraryList.clear();
                    connectionBD();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ResultSet rs = getLibraryTitleFromDB();
                    stdOut.println("rs: " + rs);
                    while(rs.next()) {
                        String nameLibrary = rs.getString("library_title");
                        stdOut.println("Name: " + nameLibrary);
                        libraryList.add(new ViewListLibrary(nameLibrary));
                        stdOut.println("tableList: " + libraryList);
                    }
                    stdOut.println("\n");
                    libraryList.forEach(System.out::println);
                    tableListLibrary.setItems(libraryList);
                    connectionClose();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    /********************************************************************************
     *                              Other Methods                                   *
     * *****************************************************************************/
    public String getLibraryTitle(){
        return libraryTitle;
    }
}
