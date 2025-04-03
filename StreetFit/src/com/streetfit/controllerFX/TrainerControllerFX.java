package com.streetfit.controllerfx;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class TrainerControllerFX {


	    @FXML private Label trainerLabel;

	    @FXML
	    public void initialize() {
	        trainerLabel.setText("Dummy, login riuscito!");
	    }

	    @FXML
	    private void logout() {
	        // Chiude la finestra attuale (simulazione logout)
	        Stage stage = (Stage) trainerLabel.getScene().getWindow();
	        stage.close();
	    }
	}

