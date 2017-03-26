package com.devprosony.core;

import com.devprosony.Main;
import com.devprosony.core.controllers.GetLibrary;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import static com.devprosony.Main.setHeightAndWidth;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
abstract public class SceneManager {

    private static Scene sceneSingIn;
    private static Scene sceneMain;


    private static double dragOffsetX;
    private static double dragOffsetY;

    private static Stage primaryStage;
    private static Stage dialogStage;

    protected SceneManager(){
        primaryStage = Main.getPrimaryStage();
    }

    public void switchScene(Scene sceneInitial) {
        //stdOut.println("sceneOne equals: " + primaryStage.getScene().equals(sceneSingIn));
        try {
            sceneMain = new Scene(FXMLLoader.load(getClass().getResource("core/controllers/view/main.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("switchScene");
        primaryStage.setScene(sceneMain);
        setHeightAndWidth(600.0,800.0, 1920.0,1080.0);
        movingScene(sceneMain);

    }
    public void loadScene(){
        try {
            sceneSingIn = new Scene(FXMLLoader.load(getClass().getResource("core/controllers/view/singInPanel.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setOpacity(0.9);
        primaryStage.centerOnScreen();
        primaryStage.setScene(sceneSingIn);
        primaryStage.show();
        movingScene(sceneSingIn);
    }


    public void showTableListLibrary() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("core/controllers/view/GetLibrary.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            dialogStage = new Stage();
            //dialogStage.setTitle("Open Library");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            GetLibrary controllerLibrary = loader.getController();
            controllerLibrary.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.show();
            // return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * _______________________Method_for_move_primaryStage____________________________
     */
    private void movingScene(Scene scene) {

        scene.setOnMousePressed(SceneManager::handleMousePressed);
        scene.setOnMouseDragged(SceneManager::handleMouseDragged);
    }

    private static void handleMouseDragged(MouseEvent e) {
        // Move the stage by the drag amount
        primaryStage.setX(e.getScreenX() - dragOffsetX);
        primaryStage.setY(e.getScreenY() - dragOffsetY);
    }

    private static void handleMousePressed(MouseEvent e) {
        // Store the mouse x and y coordinates with respect to the
        // stage in the reference variables to use them in the drag event
        dragOffsetX = e.getScreenX() - primaryStage.getX();
        dragOffsetY = e.getScreenY() - primaryStage.getY();
    }

}
