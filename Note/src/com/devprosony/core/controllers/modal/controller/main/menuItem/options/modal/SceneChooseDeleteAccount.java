package com.devprosony.core.controllers.modal.controller.main.menuItem.options.modal;

import javafx.stage.Stage;

import static com.devprosony.Main.sceneManager;

public class SceneChooseDeleteAccount {
    boolean deleteAccount = false;

    Stage stageSceneModalDeleteAccount;

    public void buttonClickCancelDeleteAccount() {
        stageSceneModalDeleteAccount.close();
    }

    public boolean buttonClickOkDeleteAccount() {
        deleteAccount = true;
        //sceneManager.deleteAccount();
        stageSceneModalDeleteAccount.close();
        return true;
    }
    public void setStageModal(Stage stageSceneModalDeleteAccount){
        this.stageSceneModalDeleteAccount = stageSceneModalDeleteAccount;
    }

    public boolean getDeleteAccount(){
        return deleteAccount;
    }
}
