package com.devprosony.core.controllers;

import com.devprosony.core.ConnectionToBD;
import com.devprosony.core.SceneManager;
import com.devprosony.core.controllers.model.ViewListLibrary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenLibraryManager extends ConnectionToBD {

        @FXML
        public TableView<ViewListLibrary> tableListLibrary;
        @FXML
        public TableColumn<ViewListLibrary, String> nameLibrary;

        private Stage dialogStage;
        private ObservableList<ViewListLibrary> libraryList = FXCollections.observableArrayList();

        public ScreenLibraryManager() throws SQLException {
        }

        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;

        }
        @FXML
        public String openLibrary(){
            String nameLibrary = String.valueOf(tableListLibrary.getSelectionModel().getSelectedItem());
            stdOut.println("\ntext: " + nameLibrary);
            dialogStage.close();
            return  nameLibrary;
        }
        @FXML
        private String handleCancel() {

            dialogStage.close();
            return null;
        }

        @FXML
        private void initialize() {
            nameLibrary.setCellValueFactory(new PropertyValueFactory<ViewListLibrary, String>("nameLibrary"));
            Thread threadSetTableListLibrary = new ThreadSetTableListLibrary();
            threadSetTableListLibrary.start();
        }


    /**
     * The thread takes name library from the DB and fills the Table
     */
    class ThreadSetTableListLibrary extends Thread{
            @Override
            public void run(){
                try {
                    connectionBD();
                    ResultSet rs;
                    rs = stmt.executeQuery("select personal_library.library_title " +
                            "from personal_library " +
                            "join account i2 on personal_library.id_account = i2.id_this_account;");
                    while(rs.next()) {

                        String nameLibrary = rs.getString("library_title");
                        stdOut.println("Name: " + nameLibrary);
                        libraryList.add(new ViewListLibrary(nameLibrary));
                        stdOut.println("tableList: " + libraryList);

                    }
                    libraryList.forEach(System.out::println);
                    tableListLibrary.setItems(libraryList);
                    connectionClose();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

}
