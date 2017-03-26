package com.devprosony.core.controllers;

import com.devprosony.Main;
import com.devprosony.core.ConnectionToBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lib.StdOut;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.sceneManager;


/**
 * Created by ${Prosony} on ${24.01.2016}.
 */
public class singInPanelController extends ConnectionToBD {

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

    private static StdOut stdOut = new StdOut();
    private Stage primaryStage;

    public singInPanelController() throws SQLException {
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

                                        stdOut.println("log in is success!");
                                        stdOut.println("decorated style...");
                                        sceneManager.switchScene(primaryStage.getScene());

                    }else {
                                        progressLabel.setText("Invalid\n login\nor\n password");
                                        stdOut.println("Invalid login or password");
                    }
                }
                connectionClose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void exit(){
        System.exit(0);
    }


}
