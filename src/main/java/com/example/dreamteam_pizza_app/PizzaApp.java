package com.example.dreamteam_pizza_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class PizzaApp extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PizzaApp.class.getResource("customer_dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("DreamTeam Pizza App");
        primaryStage.setScene(scene);
        primaryStage.show();

        CustomerDashboardController controller = fxmlLoader.getController();//this is the controller
        controller.testingChange();//this is the method in the controller
    }
}
