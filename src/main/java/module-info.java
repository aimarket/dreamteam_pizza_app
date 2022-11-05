module com.example.dreamteam_pizza_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dreamteam_pizza_app to javafx.fxml;
    exports com.example.dreamteam_pizza_app;
}