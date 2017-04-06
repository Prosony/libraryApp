package com.devprosony.core.controllers.manager.stage;

import com.devprosony.Main;
import com.devprosony.core.controllers.modal.controller.library.ScreenCreateLibrary;
import com.devprosony.core.controllers.modal.controller.library.ScreenLibraryManager;
import com.devprosony.core.controllers.modal.controller.library.ScreenRenameLibrary;
import com.devprosony.core.controllers.modal.controller.main.ScreenAddBook;
import com.devprosony.core.controllers.modal.controller.main.ScreenEditBook;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
abstract public class SceneManager {

    private static Scene sceneSingIn;
    private static Scene sceneMain;
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

    public void switchScene() {
     try {
            sceneMain = new Scene(FXMLLoader.load(getClass().getResource(
                        "core/controllers/view/ScreenMain.fxml")));
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
            sceneSingIn = new Scene(FXMLLoader.load(getClass().getResource(
                         "core/controllers/view/singInPanel.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setOpacity(0.9);
        primaryStage.centerOnScreen();
        primaryStage.setScene(sceneSingIn);
        primaryStage.show();
        movingMainStage(sceneSingIn);
    }

/********************************************************************************
*                       Show Panel SelectionLibrary                             *
* ******************************************************************************/
    public String showPanelLibrary() {
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
            String libraryTitle = controllerLibrary.getLibraryTitle();
            stdOut.println("libraryTitle: " + libraryTitle);
            return libraryTitle;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

/********************************************************************************
*                       Create a pane for the context menu                      *
* *********************************************************************************************
                 *                       Show panel for Rename library                          *
                 * *****************************************************************************/
                public boolean showPanelRenameLibrary(String oldlibraryTitle) throws IOException {
                    //String newLibraryTitle = null;

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

                    return screenRenameLibrary.getNewLibraryTitle();
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
    public void showPanelEditBook(String oldBookTitle, String oldBookGenre) throws IOException {

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
        screenEditBook.setDialogeStageAndOldBookData(dialogEditBookStage, oldBookTitle, oldBookGenre);
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
}
