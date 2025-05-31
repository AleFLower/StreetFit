package main.java.com.streetfit.controllerfx;

import main.java.com.streetfit.model.Credentials;
import main.java.com.streetfit.utils.NotificationQueue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle; 

public class HomeGUIControllerFX {

	public void loadNextScene(Stage stage, Credentials cred) {
	    try {
	        NotificationQueue notificationQueue = new NotificationQueue();

	        FXMLLoader loader;
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

	        // Controller factory per passare le dipendenze
	        loader.setControllerFactory(param -> {
	            if (param == TrainerControllerFX.class) {
	                return new TrainerControllerFX(cred, notificationQueue);
	            } else if (param == ParticipantControllerFX.class) {
	                return new ParticipantControllerFX(cred, notificationQueue);
	            } else {
	                try {
	                    return param.getDeclaredConstructor().newInstance();
	                } catch (Exception e) {
	                    throw new RuntimeException(e);
	                }
	            }
	        });

	        Parent root = loader.load();

	        Scene scene = new Scene(root);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.getIcons().add(new Image("Images/pull-up-bar.png"));
	        stage.setScene(scene);
	        stage.show();

	    } catch (Exception e) {
	        throw new IllegalArgumentException("Error by loading FXML", e);
	    }
	}

}
