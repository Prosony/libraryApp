package com.devprosony.core.controllers.model;

/**
 * Created by Prosony on 22/03/17.
 */
public class TableDataView {

    private String nameBook;

    public TableDataView(String nameBook) {
        this.nameBook = nameBook;
    }

    public TableDataView() {}

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    @Override
    public String toString() {
        return  nameBook;
    }
}