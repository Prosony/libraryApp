package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
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

    private Stage dialogEditBookStage;
    private String oldBookTitle;
    private String oldBookGenre;
    private int oldIdAuthor;

    @FXML private void initialize() {}

    public void clickEditBook(ActionEvent actionEvent) {}
    public void clickCancelEditBook(ActionEvent actionEvent) {
        dialogEditBookStage.close();
    }

    public void setDialogeStageAndOldBookData(Stage dialogEditBookStage, String oldBookTitle, String oldBookGenre){

        this.dialogEditBookStage = dialogEditBookStage;
        this.oldBookTitle = oldBookTitle;
        textFieldNewBookTitle.setText(oldBookTitle);
        textFieldNewBookGenre.setText(oldBookGenre);
        textFieldNewAuthorBook.setText("");
        stdOut.println("oldLibraryTitle: " + this.oldBookTitle);

    }
}
