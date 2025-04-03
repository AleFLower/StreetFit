package com.streetfit.controllerfx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;

import com.streetfit.model.Credentials;
import com.streetfit.controller.LoginController;

import javafx.stage.Stage;

public class LoginGUIControllerFX {

    @FXML private TextField siusername;
    @FXML private PasswordField sipassword;
    @FXML private Button siloginBtn;



    @FXML
    public void login() {
        String username = siusername.getText();
        String password = sipassword.getText();
        final String msg = "Error";

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(msg, "Username e password non possono essere vuoti.");
            return;
        }

        try {
            LoginController loginController = new LoginController();
            Credentials credentials = loginController.login(username, password);

            if (credentials.getRole() != null) {
            	 Alert alert;
                
            	 alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Information Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Successfully Login!");
                 alert.showAndWait();
                 siloginBtn.getScene().getWindow().hide();
       
                 Stage stage = new Stage();
                 HomeGUIControllerFX homeController = new HomeGUIControllerFX();
              
                 homeController.loadNextScene(stage, credentials);
                 
                
            } else {
                showAlert(msg, "Credenziali non valide.");
            }

        } catch (RuntimeException e) {
        	System.out.println("Error: "+ e.getMessage());
            showAlert(msg, e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void close() {
        javafx.application.Platform.exit();
    }
}
