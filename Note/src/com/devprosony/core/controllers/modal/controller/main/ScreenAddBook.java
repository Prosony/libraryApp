package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import com.sun.xml.internal.bind.v2.TODO;
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
    @FXML TextField textFieldAuthorBook;

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
         *  addBookIntoPersonalLibrary() <- String  bookTitle, String genreBook, int idAuthor
         */
        String bookTitle = textFieldBookTitle.getText();
        String bookGenre = textFieldBookGenre.getText();
        String bookAuthor = textFieldAuthorBook.getText();
        int idAuthorBook = 0;
        if (bookAuthor != "null"){
            idAuthorBook = getIdAuthor(bookAuthor);
        }
        stdOut.println("idAuthorBook: " + idAuthorBook);
        if ((bookTitle != "null")&&(bookGenre != "null")&&(idAuthorBook != 0)){
            //stdOut.println("go add book");
            addBookIntoPersonalLibrary(bookTitle, bookGenre,idAuthorBook);
            dialogStage.close();
        }else{
            stdOut.println("do not");
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

    public void setComboBoxAuthorBook() {
        ///comboBoxAuthorBook.getItems().addAll("5", "10", "25", "50", "100", "200", "300", "500", "1000");

    }


}