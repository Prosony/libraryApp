package com.devprosony.core.controllers.manager.stage;

import com.devprosony.Main;
import com.devprosony.core.controllers.modal.controller.ScreenLibraryManager;
import com.devprosony.core.controllers.modal.controller.ScreenRenameLibrary;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import static com.devprosony.Main.setHeightAndWidth;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
abstract public class SceneManager {

    private static Scene sceneSingIn;
    private static Scene sceneMain;
    private static double dragOffsetX;
    private static double dragOffsetY;
    private static Stage primaryStage;
    private static Stage dialogPaneLibraryStage;
    private static Stage dialogRenameLibraryStage;
    private double xOffset;
    private double yOffset;

    protected SceneManager(){
        primaryStage = Main.getPrimaryStage();
    }

    public void switchScene() {
     try {
            sceneMain = new Scene(FXMLLoader.load(getClass().getResource("core/controllers/view/main.fxml")));
     } catch (IOException e) {
            e.printStackTrace();
     }
        System.out.printf("switchScene");
        primaryStage.setScene(sceneMain);
        setHeightAndWidth(600.0,800.0, 1920.0,1080.0);
        movingMainStage(sceneMain);
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
        movingMainStage(sceneSingIn);
    }
    /********************************************************************************
     *                       Show panel for selection library                       *
     * *****************************************************************************/
    public void showPanelRenameLibrary(String oldlibraryTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("core/controllers/view/PanelRenameLibrary.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        dialogRenameLibraryStage = new Stage();
        dialogRenameLibraryStage.initModality(Modality.WINDOW_MODAL);
        dialogRenameLibraryStage.initOwner(dialogPaneLibraryStage);
        dialogRenameLibraryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(page);
        dialogRenameLibraryStage.setScene(scene);

        ScreenRenameLibrary screenRenameLibrary = loader.getController();
        screenRenameLibrary.setDialogStage(dialogRenameLibraryStage);
        screenRenameLibrary.setOldLibraryTitle(oldlibraryTitle);
        movingRenameLibraryStageModal(scene);
        dialogRenameLibraryStage.showAndWait();

    }
    /********************************************************************************
     *                       Show panel for selection library                       *
     * *****************************************************************************/
    public String showPanelLibrary() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("core/controllers/view/GetLibrary.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            dialogPaneLibraryStage = new Stage();
            dialogPaneLibraryStage.initModality(Modality.WINDOW_MODAL);
            dialogPaneLibraryStage.initOwner(primaryStage);
            dialogPaneLibraryStage.initStyle(StageStyle.TRANSPARENT);
            Scene scenePaneLibtaty = new Scene(page);
            dialogPaneLibraryStage.setScene(scenePaneLibtaty);
            // Set the person into the controller.
            ScreenLibraryManager controllerLibrary = loader.getController();
            controllerLibrary.setDialogStage(dialogPaneLibraryStage);
            /**     Show the dialog and wait until the user closes it     */
            movingLibraryStageModal(scenePaneLibtaty);
            dialogPaneLibraryStage.showAndWait();
            /**       start when dialogStage will be close                */
            stdOut.println("dialogStage closed");
            String libraryTitle = controllerLibrary.getLibraryTitle();      /////fix
            stdOut.println("libraryTitle: " + libraryTitle);
            return libraryTitle;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /********************************************************************************
     *                              Moving scene                                    *
     * *****************************************************************************/
    private void movingMainStage(Scene scene) {
        scene.setOnMousePressed(event -> {
            dragOffsetX = primaryStage.getX() - event.getScreenX();
            dragOffsetY = primaryStage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + dragOffsetX);
            primaryStage.setY(event.getScreenY() + dragOffsetY);
        });
       }
//____________________moving_Library_Stage_Modal______________________________________
    private void movingLibraryStageModal(Scene scenePaneLibtaty){
        scenePaneLibtaty.setOnMousePressed(event -> {
            dragOffsetX = dialogPaneLibraryStage.getX() - event.getScreenX();
            dragOffsetY = dialogPaneLibraryStage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler
        scenePaneLibtaty.setOnMouseDragged(event -> {
            dialogPaneLibraryStage.setX(event.getScreenX() + dragOffsetX);
            dialogPaneLibraryStage.setY(event.getScreenY() + dragOffsetY);
        });
    }
//____________________moving_Rename_Library_Stage_Modal_______________________________
    private void movingRenameLibraryStageModal(Scene scenePaneLibtaty){
        scenePaneLibtaty.setOnMousePressed(event -> {
            dragOffsetX = dialogRenameLibraryStage.getX() - event.getScreenX();
            dragOffsetY = dialogRenameLibraryStage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler
        scenePaneLibtaty.setOnMouseDragged(event -> {
            dialogRenameLibraryStage.setX(event.getScreenX() + dragOffsetX);
            dialogRenameLibraryStage.setY(event.getScreenY() + dragOffsetY);
        });
    }

}
