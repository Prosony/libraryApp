package com.devprosony.core.controllers.modal.controller.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * Created by proso on 4/10/2017.
 */
public class ScreenPartTabMain {
    @FXML Label labelBookTitle;
    @FXML Label labelAuthor;
    @FXML Label labelAboutBook;

    @FXML private void initialize() {}

    public void setLabelText(String titleBook, String bookFullNameAuthor, String about){
        labelBookTitle.setText(titleBook);
        labelAuthor.setText(bookFullNameAuthor);
        labelAboutBook.setText(about);
    }


}
