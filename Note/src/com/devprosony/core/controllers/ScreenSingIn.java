package com.devprosony.core.controllers;

import com.devprosony.Main;
import com.devprosony.core.DataBaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;
import static com.devprosony.Main.stdOut;

/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class ScreenSingIn extends DataBaseManager {

        @FXML
        PasswordField textFieldPassword;
        @FXML
        TextField textFieldLogin;
        @FXML
        Label progressLabel;
        @FXML
        Button buttonSign;
        @FXML
        Button buttonExit;
        @FXML
        AnchorPane anchorMain;

        private static boolean initilaizeMainScene = false;
        private Stage primaryStage;

        public ScreenSingIn() throws SQLException {
            this.primaryStage = Main.getPrimaryStage();
        }

        public void connectToAccount(ActionEvent actionEvent){

            connectionBD();
            String query = "select * from Account;";
            String passwordFromPasswordField = textFieldPassword.getText();
            String loginFromTextField = textFieldLogin.getText();
            progressLabel.setText("");

            try {
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    String passwordFromBD = rs.getString(3);
                    String loginFromBD = rs.getString(2);
                    stdOut.println("Login,Password: " + loginFromBD +" "+ passwordFromBD
                            + "|" + loginFromTextField +" "+ passwordFromPasswordField);

                    if ((loginFromTextField.equals(loginFromBD))&&(passwordFromPasswordField.equals(passwordFromBD))){
                        int idAccount = rs.getInt(1);
                        setIdAccount(idAccount);
                        stdOut.println("_______________________________");
                        stdOut.println("log in is success!");
                        stdOut.println("id_this_account: " + idAccount);
                        stdOut.println("decorated style...");
                        stdOut.println("_______________________________");
                        if(initilaizeMainScene){
                            sceneManager.switchScene("SceneMain");
                        }else{
                            initilaizeMainScene = true;
                            sceneManager.switchScene("LoadSceneMain");
                        }
                        progressLabel.setText("");
                        break;
                    }else {
                        progressLabel.setText("Invalid\n login\nor\n password");
                        stdOut.println("Invalid login or password");
                    }
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }

            connectionClose();
        }

        public void exit(){
            System.exit(0);
        }

}
