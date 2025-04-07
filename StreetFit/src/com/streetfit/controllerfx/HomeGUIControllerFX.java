package com.streetfit.controllerfx;

import com.streetfit.model.Credentials;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
                    loader = new FXMLLoader(getClass().getResource("/com/StreetFit/ViewFX/TrainerGUI.fxml"));
                    break;
                case PARTICIPANT:
                    loader = new FXMLLoader(getClass().getResource("ParticipantGUI.fxml"));
                    break;
                default:
                   throw new IllegalArgumentException("Error during authentication");
                  }
            

            // Crea la nuova scena
            Scene scene = new Scene(loader.load());
            
            // Imposta lo stile della finestra (puoi rimuovere questa parte se non ti serve)
            stage.initStyle(StageStyle.DECORATED);

            // Imposta la scena e mostralo
            stage.setScene(scene);
            
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
