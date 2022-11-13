package com.example.dreamteam_pizza_app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class CheffDashboardController {
    @FXML
    public Button button_staff;

    @FXML
    public TextArea textarea_order_history;

    @FXML
    public Pane pane_pizza_order;

    @FXML
    public Label label_order_status;

    @FXML
    public Label label_order_customer_name;

    @FXML
    public Label label_order_pizza_type;

    @FXML
    public Label label_order_time;

    @FXML
    public Button button_order_status_change;

    @FXML
    public ComboBox combobox_order_select_status;
}
