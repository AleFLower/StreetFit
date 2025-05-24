package com.streetfit.main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFxml/Login.fxml"));

            Scene scene = new Scene(loader.load());
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("Images/pull-up-bar.png"));
            primaryStage.setTitle("StreetFit");
            primaryStage.setResizable(false);  // Impedisce il ridimensionamento e il pulsante di ingrandimento
     
            primaryStage.show();
        } catch (Exception e) {
           throw new IllegalArgumentException("Error by loading FXML");
        }
    }

    public static void run() {
        launch();  // Metodo di JavaFX per avviare l'Application
    }
}
