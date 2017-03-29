package com.devprosony.core.controllers.modal.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import static com.devprosony.Main.stdOut;

/**
 * Created by proso on 3/29/2017.
 */
public class ScreenRenameLibrary {

    private Stage dialogRenameLibraryStage;

    public void setDialogStage(Stage dialogRenameLibraryStage) { this.dialogRenameLibraryStage = dialogRenameLibraryStage; }


    /********************************************************************************
     *                              Button ActionEvents                             *
     * *****************************************************************************/
    public void clickCancelRenameLibrary(ActionEvent actionEvent) {
        stdOut.println("Press Cancel");
        dialogRenameLibraryStage.close();
    }


}
