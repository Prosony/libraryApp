package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.devprosony.Main.stdOut;

/**
 * Created by proso on 4/5/2017.
 */
public class ScreenEditBook extends DataBaseManager{

    @FXML
    TextField textFieldNewBookTitle;
    @FXML
    TextField textFieldNewBookGenre;
    @FXML
    TextField textFieldNewAuthorBook;

    public ScreenEditBook() throws SQLException {}


    @FXML private void initialize() {}

    private Stage dialogEditBookStage;
    private String oldBookTitle;
    private String oldBookGenre;
    private String authorFullName;

    public void clickEditBook(ActionEvent actionEvent) {
        //TODO
    }

    public void clickCancelEditBook(ActionEvent actionEvent) {
        dialogEditBookStage.close();
    }

    public void setDialogeStageAndOldBookData(Stage dialogEditBookStage, String oldBookTotle){

        this.dialogEditBookStage = dialogEditBookStage;
        this.oldBookTitle = oldBookTotle;
        stdOut.println("oldLibraryTitle: " + this.oldBookTitle);
        //textFieldForNewLibraryTitle.setPromptText(oldLibraryTitle);

    }
}
