package com.devprosony.core.controllers;

import com.devprosony.core.ConnectionToBD;
import com.devprosony.core.controllers.model.ViewListLibrary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lib.StdOut;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class GetLibrary extends ConnectionToBD{

    @FXML
    public TableView<ViewListLibrary> tableListLibrary;
    @FXML
    public TableColumn<ViewListLibrary, String> nameLibrary;
    private Stage primaryStage;
    StdOut stdOut = new StdOut();

    private Stage dialogStage;
    private ObservableList<ViewListLibrary> libraryList = FXCollections.observableArrayList();

    public GetLibrary() throws SQLException {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {

        dialogStage.close();
    }

    @FXML
    private void initialize() {
        nameLibrary.setCellValueFactory(new PropertyValueFactory<ViewListLibrary, String>("nameLibrary"));
        Thread threadSetTableListLibrary = new ThreadSetTableListLibrary();
        threadSetTableListLibrary.start();
    }

    class ThreadSetTableListLibrary extends Thread{
        @Override
        public void run(){
            try {
                connectionBD();
                ResultSet rs;
                rs = stmt.executeQuery("select name_library from personal_library;");
                while(rs.next()) {
                    String nameLibrary = rs.getString("name_library");

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
