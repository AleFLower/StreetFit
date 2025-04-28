package com.streetfit.controllerfx;

import com.streetfit.model.Credentials;

import javafx.fxml.FXMLLoader;
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
                    loader = new FXMLLoader(getClass().getResource("/com/streetFit/viewfx/Trainerdashboard.fxml"));
                    break;
                case PARTICIPANT:
                    loader = new FXMLLoader(getClass().getResource("ParticipantGUI.fxml"));
                    break;
                default:
                   throw new IllegalArgumentException("Error during authentication");
                  }
            

            // Crea la nuova scena
            Scene scene = new Scene(loader.load());
            
            
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("Images/pull-up-bar.png"));

            // Imposta la scena e mostralo
            stage.setScene(scene);
            
            stage.show();

        } catch (Exception e) {
        	e.printStackTrace();
          throw new IllegalArgumentException("Error by loading FXML");
        }
    }
}
