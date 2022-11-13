package com.example.dreamteam_pizza_app;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustomerDashboardController {
    @FXML
    public Label text_status;
    @FXML
    public ProgressBar progressbar_status;
    @FXML
    public Button button_customer;
    @FXML
    public Button button_add_to_order;
    @FXML
    public Button button_placeorder;
    @FXML
    public Button button_status;
    @FXML
    public RadioButton rbutton_pepperoni;
    @FXML
    public RadioButton rbutton_cheese;
    @FXML
    public RadioButton rbutton_Vegetable;
    @FXML
    public CheckBox checkbox_mushroom;
    @FXML
    public CheckBox checkbox_olives;
    @FXML
    public CheckBox checkbox_onion;
    @FXML
    public CheckBox checkbox_extra_cheese;
    @FXML
    public TextArea textarea_order;
    @FXML
    public TextField textfield_asurite;
    @FXML
    public TextField textfield_asurite_status;
    @FXML
    public ComboBox combobox_time;
    @FXML
    public ComboBox combobox_ampm;

    @FXML
    public void testingChange() {
        text_status.setText("cooking");
    }

}
