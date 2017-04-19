package com.devprosony.core.controllers.modal.controller.main.menuItem.options;

import com.devprosony.core.DataBaseManager;
import javafx.stage.Stage;

import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on 4/18/2017.
 */
public class ScreenOptions extends DataBaseManager{

    Stage stageSceneOptions;

    public ScreenOptions() throws SQLException {}

    public void clickButtonDeleteAccount() {
    boolean deleteAccount = sceneManager.showPaneModalDeleteAccount(stageSceneOptions);
        if (deleteAccount){
            deleteAccount();
            stageSceneOptions.close();
            sceneManager.switchScene("SceneChoose");
            stdOut.println("Heey");
        }
    }
    public void clickButtonCloseStageOption() {
        stageSceneOptions.close();
    }
    public void setStageAndOther(Stage stageSceneOptions) {
        this.stageSceneOptions = stageSceneOptions;
    }
}
