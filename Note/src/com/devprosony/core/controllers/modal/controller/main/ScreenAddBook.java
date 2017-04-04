package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.devprosony.Main.stdOut;

/**
 * Created by Prosony on 4/4/2017.
 */
public class ScreenAddBook extends DataBaseManager{

    @FXML TextField textFieldBookTitle;
    @FXML TextField textFieldBookGenre;
    @FXML ComboBox comboBoxAuthorBook;

    private Stage dialogStage;

    public ScreenAddBook() throws SQLException {}

    @FXML private void initialize() {}
    /********************************************************************************
     *                              Button ActionEvents                             *
     * *****************************************************************************/
    public void clickAddBook(ActionEvent actionEvent) {
        /*
         * insert into books(id_personal_library, book_title, id_author, genre)
         * values(4,'Effective Java 2nd Edition', 5, 'Computers & Technology');
         */
    }

    public void clickCancelAddBook(ActionEvent actionEvent) {
        stdOut.println("Press Cancel");
        dialogStage.close();
    }

    /********************************************************************************
     *                              Other Methods                                   *
     * *****************************************************************************/
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setComboBoxAuthorBook() {
        ///comboBoxAuthorBook.getItems().addAll("5", "10", "25", "50", "100", "200", "300", "500", "1000");

    }


}