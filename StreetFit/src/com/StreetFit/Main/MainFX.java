package com.streetfit.Main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/streetFit/ViewFX/Login.fxml"));

            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("StreetFit");
            primaryStage.setResizable(false);  // Impedisce il ridimensionamento e il pulsante di ingrandimento
     
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run() {
        launch();  // Metodo di JavaFX per avviare l'Application
    }
}
