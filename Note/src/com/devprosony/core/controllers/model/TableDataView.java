package com.devprosony.core.controllers.model;
/**
 * Created by Prosony on 22/03/17.
 */
public class TableDataView {

    private String bookTitle;

        public TableDataView(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public TableDataView() {
        }

        public String getbookTitle() {
            return bookTitle;
        }

        public void setbookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

    @Override
    public String toString() {
        return  bookTitle;
    }
}