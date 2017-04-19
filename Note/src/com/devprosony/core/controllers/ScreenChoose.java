package com.devprosony.core.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.devprosony.Main.sceneManager;

/**
 * Created by ${Prosony} on 4/17/2017.
 */
public class ScreenChoose {

    private boolean loadGetStartedTriger = false;
    private boolean loadLogIn = false;

    public void clickButtonGetStartedLoad() {
        if (!loadGetStartedTriger){
            loadGetStartedTriger = true;
            sceneManager.switchScene("LoadSceneRegistration");
        }else{
            sceneManager.switchScene("SceneRegistration");
        }
    }
    public void clickButtonLogInLoad(ActionEvent actionEvent) {
        if (!loadLogIn){
            loadLogIn = true;
            sceneManager.switchScene("LoadSceneLogIn");
        }else{
            sceneManager.switchScene("SceneLogIn");
        }
    }

    public void clickButtonExit() {
        System.exit(0);
    }
}
