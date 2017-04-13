package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by Prosony on 4/4/2017.
 */
public class ScreenAddBook extends DataBaseManager{

    @FXML TextField textFieldBookTitle;
    @FXML TextField textFieldBookGenre;
    @FXML TextField textFieldAuthorBook;
    @FXML TextArea areaFieldAboutBook;

    private Stage dialogStage;

    public ScreenAddBook() throws SQLException {}

    @FXML private void initialize() {}
    /********************************************************************************
     *                              Button ActionEvents                             *
     * *****************************************************************************/
    public void clickAddBook(ActionEvent actionEvent) {

        boolean addBook = true;
        String bookTitle = textFieldBookTitle.getText();
        String bookGenre = textFieldBookGenre.getText();
        String bookAuthor = textFieldAuthorBook.getText();
        String bookAbout = areaFieldAboutBook.getText();
        int idAuthorBook = 0;

        if (bookAuthor != "null"){
            idAuthorBook = getDataAuthor(bookAuthor);
        }
        if ((bookTitle != "null")&&(bookGenre != "null")&&(idAuthorBook != 0)){
            dialogStage.close();
        }else{
            stdOut.println("do not");
        }
        connectionBD();
        ResultSet rs = getFullDataAboutBookFromSelectionLibraryFromDB();
        try {
            while(rs.next()){
                String bookTitleFromBD = rs.getString("book_title");
                if(bookTitleFromBD.equals(bookTitle)){
                    addBook = false;
                    sceneManager.showNotifications("error", "can't add this book");
                }
            }
            connectionClose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(addBook){
            addBookIntoPersonalLibrary(bookTitle, bookGenre, idAuthorBook, bookAbout);
        }

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

}