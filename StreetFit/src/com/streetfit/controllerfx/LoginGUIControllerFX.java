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

    @FXML private TextField si_username;
    @FXML private PasswordField si_password;
    @FXML private Button si_loginBtn;

    private HomeGUIControllerFX homeController;  // Riferimento al controller HomeGUI


    @FXML
    public void login() {
        String username = si_username.getText();
        String password = si_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Errore", "Username e password non possono essere vuoti.");
            return;
        }

        try {
            LoginController loginController = new LoginController();
            Credentials credentials = loginController.login(username, password);

            if (credentials.getRole() != null) {
            	 Alert alert;
                // Quando il login è riuscito, passiamo le credenziali al controller della Home
            	 alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Information Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Successfully Login!");
                 alert.showAndWait();
                 si_loginBtn.getScene().getWindow().hide();
       
                 Stage stage = new Stage();
                 homeController = new HomeGUIControllerFX();
              
                 homeController.loadNextScene(stage, credentials);
                 
                
            } else {
                showAlert("Errore", "Credenziali non valide.");
            }

        } catch (RuntimeException e) {
        	System.out.println("Error: "+ e.getMessage());
            showAlert("Errore", e.getMessage());
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
