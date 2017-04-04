package com.devprosony.core.controllers.modal.controller.library;

/**
 * Created by Prosony on 4/3/2017.
 */
import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;

public class ScreenCreateLibrary extends DataBaseManager {

    Stage dialogCreateLibraryStage;
    @FXML private TextField textFieldCreateLibraryTitle;

    @FXML private void initialize() {}
    public ScreenCreateLibrary() throws SQLException {}


    public void clickCreateLibrary(ActionEvent actionEvent) {
        String newLibraryTitle = textFieldCreateLibraryTitle.getText();
        createLibrary(newLibraryTitle);
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
