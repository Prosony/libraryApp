package com.devprosony.core.controllers.modal.controller.library;

/**
 * Created by Prosony on 4/3/2017.
 */
import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

public class ScreenCreateLibrary extends DataBaseManager {

    Stage dialogCreateLibraryStage;
    @FXML private TextField textFieldCreateLibraryTitle;

    @FXML private void initialize() {}
    public ScreenCreateLibrary() throws SQLException {}


    public void clickCreateLibrary(ActionEvent actionEvent) {
        boolean creatLibrary = true;
        String newLibraryTitle = textFieldCreateLibraryTitle.getText();
        connectionBD();
        sceneManager.showNotifications("error", "can't add this book");
        ResultSet rs = getDataLibraryFromDB();
        try {
            while(rs.next()){
                String libraryTitleFromDB = rs.getString("library_title");

                if(newLibraryTitle.equals(libraryTitleFromDB)){
                    creatLibrary = false;

                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(creatLibrary){
            sceneManager.showNotifications("error", "can't add this book");
            createLibrary(newLibraryTitle);
        }
        connectionClose();
        dialogCreateLibraryStage.close();
    }

    public void clickCancelCreateLibrary(ActionEvent actionEvent) {
    dialogCreateLibraryStage.close();
    }

    /********************************************************************************
     *                              Other Methods                                    *
     * ******************************************************************************/
    public void setDialogStage(Stage dialogCreateLibraryStage){
        this.dialogCreateLibraryStage = dialogCreateLibraryStage;
    }
}
