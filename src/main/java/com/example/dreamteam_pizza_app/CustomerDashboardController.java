package com.example.dreamteam_pizza_app;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustomerDashboardController {
    @FXML
    private Label text_status;
    @FXML
    private ProgressBar progressbar_status;
    @FXML
    private Button button_customer;
    @FXML
    private Button button_add_to_order;
    @FXML
    private Button button_placeorder;
    @FXML
    private Button button_status;
    @FXML
    private RadioButton rbutton_pepperoni;
    @FXML
    private RadioButton rbutton_cheese;
    @FXML
    private RadioButton rbutton_Vegetable;
    @FXML
    private CheckBox checkbox_mushroom;
    @FXML
    private CheckBox checkbox_olives;
    @FXML
    private CheckBox checkbox_onion;
    @FXML
    private CheckBox checkbox_extra_cheese;
    @FXML
    private TextArea textarea_order;
    @FXML
    private TextField textfield_asurite;
    @FXML
    private TextField textfield_asurite_status;
    @FXML
    private ComboBox combobox_time;
    @FXML
    private ComboBox combobox_ampm;

    @FXML
    protected void testingChange() {
        text_status.setText("cooking");
    }
}
