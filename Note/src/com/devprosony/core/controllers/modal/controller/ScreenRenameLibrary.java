package com.devprosony.core.controllers.modal.controller;

import com.devprosony.core.ConnectionToBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.devprosony.Main.stdOut;

/**
 * Created by proso on 3/29/2017.
 */
public class ScreenRenameLibrary extends ConnectionToBD{
    @FXML
    TextField textFieldForNewLibraryTitle;

    private Stage dialogRenameLibraryStage;
    private String oldLibraryTitle;

    public ScreenRenameLibrary() throws SQLException {}

    public void setDialogStage(Stage dialogRenameLibraryStage) { this.dialogRenameLibraryStage = dialogRenameLibraryStage; }

    @FXML
    private void initialize() {

    }

    /********************************************************************************
     *                              Button ActionEvents                             *
     * *****************************************************************************/
    public void clickRenameLibrary(ActionEvent actionEvent) {
        String newLibraryTitle = textFieldForNewLibraryTitle.getText();

        if (newLibraryTitle.equals(oldLibraryTitle)){
            stdOut.println("newLibraryTitle = oldLibraryTitle");
        }else {
            stdOut.println("newLibraryTitle: " + newLibraryTitle);
            renameLibraryTitle(oldLibraryTitle, newLibraryTitle);
            dialogRenameLibraryStage.close();
        }
    }
    public void clickCancelRenameLibrary(ActionEvent actionEvent) {
        stdOut.println("Press Cancel");
        dialogRenameLibraryStage.close();
    }
    /********************************************************************************
    *                              Other Methods                                    *
    * ******************************************************************************/
    public void setOldLibraryTitle(String libraryTitle){
        oldLibraryTitle = libraryTitle;
        stdOut.println("oldLibraryTitle: " + oldLibraryTitle);
        textFieldForNewLibraryTitle.setPromptText(oldLibraryTitle);
    }

}
