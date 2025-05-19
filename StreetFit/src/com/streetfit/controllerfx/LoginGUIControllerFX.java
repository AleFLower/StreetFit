package com.streetfit.controllerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import com.streetfit.model.Credentials;
import com.streetfit.model.Role;
import com.streetfit.beans.CredentialsBean;
import com.streetfit.controller.LoginController;

import javafx.stage.Stage;


public class LoginGUIControllerFX {
	
	@FXML private TextField siUsername;
	@FXML private PasswordField siPassword;
	@FXML private Button siLoginBtn;
	@FXML private Button suLoginBtn;
	@FXML private Button subSignupBtn;
	@FXML private AnchorPane signupForm ;
	@FXML private AnchorPane loginForm ;
	@FXML private Button subLoginBtn;
	@FXML private Label editlabel;
	@FXML private TextField suUsername;
	@FXML private TextField suPassword;
	
	private  LoginController loginController = new LoginController();
	
    @FXML
    public void login() {
        String username = siUsername.getText();
        String password = siPassword.getText();
        final String msg = "Error";

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(msg, "Username e password non possono essere vuoti.");
            return;
        }
   

        try {
           
            Credentials credentials = loginController.login(username, password);  //attention: it must be the bean! Not directly credentials

            if (credentials.getRole() != null) {
            	 Alert alert;
                
            	 alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Information Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Successfully Login!");
                 alert.showAndWait();
                 siLoginBtn.getScene().getWindow().hide();
       
                 Stage stage = new Stage();
                 HomeGUIControllerFX homeController = new HomeGUIControllerFX();
              
                 homeController.loadNextScene(stage, credentials);
                 
                
            } else {
                showAlert(msg, "Credenziali non valide.");
            }

        } catch (RuntimeException e) {
        	
            showAlert(msg, e.getMessage());
        }
    }
    
    public void signup() {
    	String username = suUsername.getText();
    	String password = suPassword.getText();
    	
    	CredentialsBean cred = new CredentialsBean(username,password,Role.PARTICIPANT); //per ora, supponiamo solo un trainer e piu participant, dopo magari modifico
        loginController.signup(new Credentials(cred.getUsername(), cred.getPassword(), cred.getRole()));
        
        Alert alert;
        
   	     alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully signed!");
        alert.showAndWait();
    	
    }
    
    @FXML
    public void switchForm(ActionEvent event) {
        if (event.getSource() == subSignupBtn) {
            loginForm.setVisible(false);
            signupForm.setVisible(true);
            subSignupBtn.setVisible(false);
            subLoginBtn.setVisible(true);
            editlabel.setText("Already have an account?");
            editlabel.setLayoutX(37);
        } else if (event.getSource() == subLoginBtn) {
            signupForm.setVisible(false);
            loginForm.setVisible(true);
            subSignupBtn.setVisible(true);
            subLoginBtn.setVisible(false);
            editlabel.setText("Create Account");
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
