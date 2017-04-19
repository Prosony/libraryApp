package com.devprosony.core.controllers;

import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;

/**
 * Created by ${Prosony} on 4/17/2017.
 */
public class ScreenRegistration extends DataBaseManager{

    @FXML private TextField textFieldLogin;
    @FXML private TextField textFieldPassword;


    public ScreenRegistration() throws SQLException {}

    public void clickButtonSingIn(ActionEvent actionEvent) {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        creteAccount(login, password);
        sceneManager.switchScene("SceneChoose");
    }

    public void clickButtonCansel(ActionEvent actionEvent) {
            sceneManager.switchScene("SceneChoose");
    }

}
