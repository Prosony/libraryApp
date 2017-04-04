package com.devprosony.core.controllers.model;
/**
 * Created by Prosony on 22/03/17.
 */
public class TableDataView {

    private String booksTitle;

    public TableDataView(String booksTitle) {
        this.booksTitle = booksTitle;
    }
    public TableDataView() {}

    public String getBookTitle() {
        return booksTitle;
    }

    public void setBookTitle(String titleBooks) {
        this.booksTitle = booksTitle;
    }

}