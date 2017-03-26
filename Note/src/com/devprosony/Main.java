package com.devprosony;

import com.devprosony.core.SceneManager;
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


        //    Screen2Controller.setDialogStage(primaryStage);

    public static void setHeightAndWidth(Double height, Double width, Double maxHeight, Double maxWidth) {
            if (maxWidth > 800) {
                primaryStage.setOpacity(1);
            }
            primaryStage.setHeight(height);
            primaryStage.setMinHeight(height);
            primaryStage.setMaxHeight(maxHeight);
            primaryStage.setWidth(width);
            primaryStage.setMinWidth(width);
            primaryStage.setMaxWidth(maxWidth);
        }
    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
     launch(args);
    }
}
