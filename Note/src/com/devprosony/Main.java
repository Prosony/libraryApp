package com.devprosony;

import com.devprosony.core.controllers.manager.stage.SceneManager;
import javafx.application.Application;

import javafx.stage.Stage;
import lib.StdOut;

import java.io.IOException;

public class Main extends Application{

    public static SceneManager sceneManager;
    private static Stage primaryStage;
    public static StdOut stdOut = new StdOut();

        @Override
        public void start(Stage primaryStage) throws IOException {
            Main.primaryStage = primaryStage;
            sceneManager = new SceneManager() {
                @Override
                public void loadScene() {
                    super.loadScene();
                }
            };
        sceneManager.loadScene();
        }

    /**
     * Returns the main stage.
     * @return
     */
    public static void main(String[] args) {
     launch(args);
    }
/********************************************************************************
 *                              Moving scene                                    *
 * *****************************************************************************/
    public static Stage getPrimaryStage() {
    return primaryStage;
}

}
