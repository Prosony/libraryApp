package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.devprosony.Main.stdOut;

/**
 * Created by proso on 4/5/2017.
 */
public class ScreenEditBook extends DataBaseManager{

    @FXML TextField textFieldNewBookTitle;
    @FXML TextField textFieldNewBookGenre;
    @FXML TextField textFieldNewAuthorBook;
    @FXML TextArea textAreaEditAboutBook;
    public ScreenEditBook() throws SQLException {}

    private Stage dialogEditBookStage;

    private int idBook;
    private int idOldAuthor;
    private int idNewAuthor;
    private String oldBookTitle;
    private String oldGenreBook;
    private String oldFullNameAuthor;
    private String oldAboutBook;
    private String newAboutBook;
    private String newBookTitle;
    private String newBookGenre;
    private String newFullNameAuthorBook;

    private int idRelationships;

    @FXML private void initialize() {}

    public void clickEditBook(ActionEvent actionEvent) {
        newBookTitle = textFieldNewBookTitle.getText();
        newBookGenre = textFieldNewBookGenre.getText();
        newFullNameAuthorBook = textFieldNewAuthorBook.getText();

        if (!(newBookTitle.equals(oldBookTitle))){
            updataDataBook("title",newBookTitle, oldBookTitle);
        }
        if(!(newBookGenre.equals(oldGenreBook))){
            updataDataBook("genre",newBookGenre, oldGenreBook);
        }
        if (!(newFullNameAuthorBook.equals(oldFullNameAuthor))){
            idNewAuthor = getDataAuthor(newFullNameAuthorBook);
            if(idNewAuthor != 0){
                updateRelationships(idRelationships, idNewAuthor);
            }else{
                /**
                 * Method add new Author
                 */
                stdOut.println("can't find author: " + newFullNameAuthorBook);
            }
        }
        dialogEditBookStage.close();
    }

    public void clickCancelEditBook(ActionEvent actionEvent) {
        dialogEditBookStage.close();
    }

    public void setDialogeStageAndOldBookData(Stage dialogEditBookStage, int  idRelationships, int idBook, int oldIdAuthor,
                                              String oldBookTitle, String oldGenreBook, String oldFullNameAuthor, String oldAboutBook){
        this.idRelationships = idRelationships;
        this.idBook = idBook;
        this.idOldAuthor = idOldAuthor;
        this.dialogEditBookStage = dialogEditBookStage;
        this.oldBookTitle = oldBookTitle;
        this.oldGenreBook = oldGenreBook;
        this.oldAboutBook = oldAboutBook;
        this.oldFullNameAuthor = oldFullNameAuthor;
        stdOut.println("____________From SceneEditBook____________________");
        stdOut.println("this.idRelationships: " + this.idRelationships);
        stdOut.println("oldIdBook: " + this.idBook);
        stdOut.println("IdAuthor: " + this.idOldAuthor);
        stdOut.println("title: " + this.oldBookTitle);
        stdOut.println("genre: " + this.oldGenreBook);
        stdOut.println("Name author: " + this.oldFullNameAuthor);
        stdOut.println("________________________________");

        textFieldNewBookTitle.setText(this.oldBookTitle);
        textFieldNewBookGenre.setText(this.oldGenreBook);
        textFieldNewAuthorBook.setText(this.oldFullNameAuthor);
    }
}
