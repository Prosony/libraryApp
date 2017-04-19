package com.devprosony.core.controllers.manager.stage;

import com.devprosony.Main;
import com.devprosony.core.controllers.modal.controller.library.ScreenCreateLibrary;
import com.devprosony.core.controllers.modal.controller.library.ScreenLibraryManager;
import com.devprosony.core.controllers.modal.controller.library.ScreenRenameLibrary;
import com.devprosony.core.controllers.modal.controller.main.ScreenAddBook;
import com.devprosony.core.controllers.modal.controller.main.ScreenEditBook;
import com.devprosony.core.controllers.modal.controller.main.ScreenSearchPanel;
import com.devprosony.core.controllers.modal.controller.main.menuItem.options.ScreenOptions;
import com.devprosony.core.controllers.modal.controller.main.menuItem.options.modal.SceneChooseDeleteAccount;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
abstract public class SceneManager {

    private static Scene sceneSingIn;
    private static Scene sceneMain;
    private static Scene sceneRegistration;
    private static Scene sceneChoose;
    private static double dragOffsetX;
    private static double dragOffsetY;
    private Stage primaryStage;
    private Stage dialogPaneLibraryStage;

    private Stage dialogRenameLibraryStage;
    private Stage dialogCreateLibraryStage;

    private Stage dialogAddBookStage;
    private Stage dialogEditBookStage;
/********************************************************************************
*                       Create SingIn and Main Stage                            *
* ******************************************************************************/

    protected SceneManager(){
        primaryStage = Main.getPrimaryStage();
    }

    public void switchScene(String triger) {

        switch(triger){
            case "SceneChoose":
                            primaryStage.setScene(sceneChoose);
                            setHeightAndWidth(200.0,250.0, 200.0,250.0);
                            primaryStage.centerOnScreen();
                            movingMainStage(sceneChoose);
                            break;
            case "SceneRegistration":
                            primaryStage.setScene(sceneRegistration);
                            setHeightAndWidth(150.0,250.0, 150.0,250.0);
                            primaryStage.centerOnScreen();
                            movingMainStage(sceneRegistration);
                            break;
            case "SceneLogIn":
                            primaryStage.setScene(sceneSingIn);
                            setHeightAndWidth(150.0,250.0, 150.0,250.0);
                            primaryStage.centerOnScreen();
                            movingMainStage(sceneSingIn);
                            break;
            case "SceneMain":
                            primaryStage.setScene(sceneMain);
                            setHeightAndWidth(600.0,800.0, 1920.0,1080.0);
                            primaryStage.centerOnScreen();
                            movingMainStage(sceneMain);
                            break;
            case "LoadSceneChoose":
                            try {
                                sceneChoose = new Scene(FXMLLoader.load(getClass().getResource(
                                        "core/controllers/view/SceneGetChoose.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            primaryStage.initStyle(StageStyle.TRANSPARENT);
                            primaryStage.centerOnScreen();
                            primaryStage.setScene(sceneChoose);
                            primaryStage.show();
                            movingMainStage(sceneChoose);
                            break;
            case "LoadSceneRegistration":
                            try {
                                sceneRegistration = new Scene(FXMLLoader.load(getClass().getResource(
                                        "core/controllers/view/SceneRegistration.fxml")));
                                primaryStage.setScene(sceneRegistration);
                                setHeightAndWidth(150.0,250.0, 150.0,250.0);
                                primaryStage.show();
                                movingMainStage(sceneRegistration);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
            case "LoadSceneLogIn":
                            try {
                                sceneSingIn = new Scene(FXMLLoader.load(getClass().getResource(
                                        "core/controllers/view/singInPanel.fxml")));
                                primaryStage.centerOnScreen();
                                primaryStage.setScene(sceneSingIn);
                                setHeightAndWidth(150.0,250.0, 150.0,250.0);
                                primaryStage.show();
                                movingMainStage(sceneSingIn);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
            case "LoadSceneMain":
                            try {
                                sceneMain = new Scene(FXMLLoader.load(getClass().getResource(
                                        "core/controllers/view/ScreenMain.fxml")));
                                System.out.printf("switchScene");
                                primaryStage.setScene(sceneMain);
                                setHeightAndWidth(600.0,800.0, 1920.0,1080.0);
                                movingMainStage(sceneMain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
        }

    }
/********************************************************************************
*                       Show Panel SelectionLibrary                             *
* ******************************************************************************/
    public void showPanelLibrary() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("core/controllers/view/library/GetLibrary.fxml"));
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

            /**     Show the dialog and wait until the user close0s it     */
            movingLibraryStageModal(scenePaneLibtaty);
            dialogPaneLibraryStage.showAndWait();
            /**       start when dialogStage will be close                */
            stdOut.println("dialogStage closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/********************************************************************************
*                       Show SearchPanel                                        *
* ******************************************************************************/
    public void showPanelSearch(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("core/controllers/view/main/PanelSearch.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage stageSceneSearch = new Stage();
            stageSceneSearch.initModality(Modality.WINDOW_MODAL);
            stageSceneSearch.initOwner(primaryStage);
            stageSceneSearch.initStyle(StageStyle.TRANSPARENT);
            Scene scenePanelSearch = new Scene(page);
            stageSceneSearch.setScene(scenePanelSearch);
            ScreenSearchPanel screenSearchPanel = loader.getController();
            screenSearchPanel.setStageAndOther(stageSceneSearch);
            movingSearchScene(stageSceneSearch, scenePanelSearch);

            stageSceneSearch.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/********************************************************************************
*                       Show OptionsPanel                                        *
* ******************************************************************************/
    public void showPanelOptions(){

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("core/controllers/view/main/menuItem/PanelOptions.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage stageSceneOptions = new Stage();
            stageSceneOptions.initModality(Modality.WINDOW_MODAL);
            stageSceneOptions.initOwner(primaryStage);
            stageSceneOptions.initStyle(StageStyle.TRANSPARENT);
            Scene sceneOptionPane = new Scene(page);
            stageSceneOptions.setScene(sceneOptionPane);

            ScreenOptions screenOptions = loader.getController();
            screenOptions.setStageAndOther(stageSceneOptions);
            movingSearchScene(stageSceneOptions, sceneOptionPane);
            stageSceneOptions.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
             /********************************************************************************
             *                                  ModalChooseDeleteAccount                     *
             * ******************************************************************************/
             public boolean showPaneModalDeleteAccount(Stage stageSceneOptions){

                 try {

                     FXMLLoader loader = new FXMLLoader();
                     loader.setLocation(Main.class.getResource("core/controllers/view/main/menuItem/modal/ModalScreenDeleteAccount.fxml"));
                     AnchorPane page = (AnchorPane) loader.load();

                     Stage stageChooseDeleteAccount = new Stage();
                     stageChooseDeleteAccount.initModality(Modality.WINDOW_MODAL);
                     stageChooseDeleteAccount.initOwner(stageSceneOptions);
                     stageChooseDeleteAccount.initStyle(StageStyle.TRANSPARENT);

                     Scene sceneModalDeleteAccountPane = new Scene(page);
                     stageChooseDeleteAccount.setScene(sceneModalDeleteAccountPane);

                     SceneChooseDeleteAccount sceneChooseDeleteAccount = loader.getController();
                     sceneChooseDeleteAccount.setStageModal(stageChooseDeleteAccount);

                     stageChooseDeleteAccount.showAndWait();
                     return sceneChooseDeleteAccount.getDeleteAccount();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 return false;
             }
/********************************************************************************
*                       Create a pane for the context menu                      *
* *********************************************************************************************
                 *                       Show panel for Rename library                          *
                 * *****************************************************************************/
                public void showPanelRenameLibrary(String oldlibraryTitle) throws IOException {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource(
                            "core/controllers/view/library/PanelRenameLibrary.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    dialogRenameLibraryStage = new Stage();
                    dialogRenameLibraryStage.initModality(Modality.WINDOW_MODAL);
                    dialogRenameLibraryStage.initOwner(dialogPaneLibraryStage);
                    dialogRenameLibraryStage.initStyle(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(page);
                    dialogRenameLibraryStage.setScene(scene);
                    ScreenRenameLibrary screenRenameLibrary = loader.getController();
                    screenRenameLibrary.setDialogeStageAndOldLibraryTitle(dialogRenameLibraryStage, oldlibraryTitle);

                    movingRenameLibraryStageModal(scene);
                    dialogRenameLibraryStage.showAndWait();
                }
                /*******************************************************************************
                *                       Show panel for Create library                          *
                * *****************************************************************************/
                public void showPanelCreateLibraryStage() throws IOException {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource(
                            "core/controllers/view/library/PanelCreateLibrary.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    dialogCreateLibraryStage = new Stage();
                    dialogCreateLibraryStage.initModality(Modality.WINDOW_MODAL);
                    dialogCreateLibraryStage.initOwner(dialogPaneLibraryStage);
                    dialogCreateLibraryStage.initStyle(StageStyle.TRANSPARENT);
                    Scene sceneCreateLibrary = new Scene(page);
                    dialogCreateLibraryStage.setScene(sceneCreateLibrary);

                    ScreenCreateLibrary screenCreateLibrary = loader.getController();
                    screenCreateLibrary.setDialogStage(dialogCreateLibraryStage);

                    movingCreateLibraryStageModal(sceneCreateLibrary);
                    dialogCreateLibraryStage.showAndWait();
                }
/********************************************************************************
 *                              Show Panel Add Book                             *
 * *****************************************************************************/
    public void showPanelAddBook() throws IOException {
        //dialogAddBookStage
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("core/controllers/view/main/PanelAddBooks.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        dialogAddBookStage = new Stage();
        dialogAddBookStage.initModality(Modality.WINDOW_MODAL);
        dialogAddBookStage.initOwner(primaryStage);
        dialogAddBookStage.initStyle(StageStyle.TRANSPARENT);
        Scene sceneAddBook = new Scene(page);
        dialogAddBookStage.setScene(sceneAddBook);

        ScreenAddBook screenAddBook = loader.getController();
        screenAddBook.setDialogStage(dialogAddBookStage);

        movingAddBookStageModal(sceneAddBook);
        dialogAddBookStage.showAndWait();
    }//(Stage dialogEditBookStage, String oldBookTotle, String oldBookGenre, String authorFullName
/********************************************************************************
 *                              Show Panel Edit Book                            *
 * *****************************************************************************/
    public void showPanelEditBook(int idRelationships, int oldIdBook, int oldIdAuthor,
                                  String oldTitleBook, String oldGenreBook, String oldFullNameAuthor, String oldAboutBook) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("core/controllers/view/main/PanelEditBooks.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        dialogEditBookStage = new Stage();
        dialogEditBookStage.initModality(Modality.WINDOW_MODAL);
        dialogEditBookStage.initOwner(primaryStage);
        dialogEditBookStage.initStyle(StageStyle.TRANSPARENT);
        Scene sceneEditBook = new Scene(page);
        dialogEditBookStage.setScene(sceneEditBook);

        ScreenEditBook screenEditBook = loader.getController();
        screenEditBook.setDialogeStageAndOldBookData(dialogEditBookStage, idRelationships, oldIdBook, oldIdAuthor,
                                                        oldTitleBook, oldGenreBook, oldFullNameAuthor, oldAboutBook);
        movingEditBookStageModal(sceneEditBook);
        dialogEditBookStage.showAndWait();
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
                //____________________moving_Create_Library_Stage_Modal_______________________________
                private void movingCreateLibraryStageModal(Scene sceneCreateLibrary){
                    sceneCreateLibrary.setOnMousePressed(event -> {
                        dragOffsetX = dialogCreateLibraryStage.getX() - event.getScreenX();
                        dragOffsetY = dialogCreateLibraryStage.getY() - event.getScreenY();
                    });
                    //Lambda mouse event handler
                    sceneCreateLibrary.setOnMouseDragged(event -> {
                        dialogCreateLibraryStage.setX(event.getScreenX() + dragOffsetX);
                        dialogCreateLibraryStage.setY(event.getScreenY() + dragOffsetY);
                    });
                }
                //____________________moving_Add_Book_Stage_Modal_______________________________
                private void movingAddBookStageModal(Scene sceneAddBook){
                    sceneAddBook.setOnMousePressed(event -> {
                        dragOffsetX = dialogAddBookStage.getX() - event.getScreenX();
                        dragOffsetY = dialogAddBookStage.getY() - event.getScreenY();
                    });
                    //Lambda mouse event handler
                    sceneAddBook.setOnMouseDragged(event -> {
                        dialogAddBookStage.setX(event.getScreenX() + dragOffsetX);
                        dialogAddBookStage.setY(event.getScreenY() + dragOffsetY);
                    });
                }
                //____________________moving_Edit_Book_Stage_Modal_______________________________
                private void movingEditBookStageModal(Scene sceneEditBook){
                    sceneEditBook.setOnMousePressed(event -> {
                        dragOffsetX = dialogEditBookStage.getX() - event.getScreenX();
                        dragOffsetY = dialogEditBookStage.getY() - event.getScreenY();
                    });
                    //Lambda mouse event handler
                    sceneEditBook.setOnMouseDragged(event -> {
                        dialogEditBookStage.setX(event.getScreenX() + dragOffsetX);
                        dialogEditBookStage.setY(event.getScreenY() + dragOffsetY);
                    });
                }
                //____________________moving_Edit_Book_Stage_Modal_______________________________
                private void movingSearchScene(Stage stage,Scene sceneSearhPane){
                    sceneSearhPane.setOnMousePressed(event -> {
                        dragOffsetX = stage.getX() - event.getScreenX();
                        dragOffsetY = stage.getY() - event.getScreenY();
                    });
                    //Lambda mouse event handler
                    sceneSearhPane.setOnMouseDragged(event -> {
                        stage.setX(event.getScreenX() + dragOffsetX);
                        stage.setY(event.getScreenY() + dragOffsetY);
                    });
                }
/********************************************************************************
*                              Other Methods                                    *
* ******************************************************************************/

    public void setHeightAndWidth(Double height, Double width, Double maxHeight, Double maxWidth) {

        primaryStage.setHeight(height);
        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(maxHeight);
        primaryStage.setWidth(width);
        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(maxWidth);
    }

    public void showNotifications(String result, String str){
        Notifications.create()
                .title(result)
                .text(str)
                .action()
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }
}
