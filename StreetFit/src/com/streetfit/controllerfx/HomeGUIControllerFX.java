package com.streetfit.controllerfx;

import com.streetfit.model.Credentials;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle; 

public class HomeGUIControllerFX {

    // Carica la scena successiva in base al ruolo
    public void loadNextScene(Stage stage, Credentials cred) {
    	
        try {
            FXMLLoader loader;
            // Carica la scena in base al ruolo dell'utente
            switch (cred.getRole()) {
                case TRAINER:
                    loader = new FXMLLoader(getClass().getResource("/ViewFxml/Trainerdashboard.fxml"));
                    break;
                case PARTICIPANT:
                    loader = new FXMLLoader(getClass().getResource("/ViewFxml/ParticipantDashBoard.fxml"));
                    break;
                default:
                   throw new IllegalArgumentException("Error during authentication");
                  }
            Parent root = loader.load();
            Object controller = loader.getController();

            // Controlla che il controller sia un'istanza di ParticipantControllerFX
            if (controller instanceof ParticipantControllerFX) {
                // Passa cred al controller
                ((ParticipantControllerFX) controller).setCredentials(cred);
            }

            // Crea la nuova scena
            Scene scene = new Scene(root);
            
            
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("Images/pull-up-bar.png"));

            // Imposta la scena e mostralo
            stage.setScene(scene);
            
            stage.show();

        } catch (Exception e) {
        	
          throw new IllegalArgumentException("Error by loading FXML");
        }
    }
}
