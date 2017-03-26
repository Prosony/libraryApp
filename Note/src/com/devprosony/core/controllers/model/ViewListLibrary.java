package com.devprosony.core.controllers.model;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ViewListLibrary {

    private String nameLibrary;

    public ViewListLibrary(String nameLibrary) {
        this.nameLibrary = nameLibrary;
    }

    public ViewListLibrary() {}

    public void setNameLibrary(String nameBook) {
        this.nameLibrary = nameBook;
    }

    public String getNameLibrary() {
        return nameLibrary;
    }

    @Override
    public String toString() {
        return  nameLibrary;
    }
}
