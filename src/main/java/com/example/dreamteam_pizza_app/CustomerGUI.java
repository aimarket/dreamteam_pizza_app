package com.example.dreamteam_pizza_app;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class CustomerGUI {
    void customer_Button(Customer customer){
        Button button = new Button(customer.toString());
    }

    void order_Status_Button(PizzaOrder Pizza){
        Button orderStatusButton = new Button("Status");
    }

    void pizza_type_Radio_Buttons(PizzaOrder Pizza){
        RadioButton cheese = new RadioButton("Cheese");
        RadioButton pepperoni = new RadioButton("Pepperoni");
        RadioButton veggi = new RadioButton("Veggi");
    }

    void pizza_toppings_checkboxes(PizzaOrder Pizza){
        CheckBox extraCheese = new CheckBox("ExtraCheese");
        CheckBox bacon = new CheckBox("Bacon");
        CheckBox mushroom = new CheckBox("Mushroom");
    }

    void add_toOrder_button(PizzaOrder Pizza){
        Button addToOrderButton = new Button("Add to Order");
    }

    void current_Order(PizzaOrder Pizza){

    }

    void ASURITE_label_textbox(Customer customer){
        Label ASURITE = new Label(customer.toString());
    }

    int pickup_Time_dropdowns(PizzaOrder Pizza){
        ComboBox timeComboBox = new ComboBox();
        timeComboBox.getItems().addAll(
                "10:00 AM",
                "10:30 AM",
                "11:00 AM",
                "11:30 AM",
                "12:00 PM",
                "12:30 PM",
                "1:00 PM",
                "1:30 PM",
                "2:00 PM",
                "2:30 PM",
                "3:00 PM",
                "3:30 PM",
                "4:00 PM",
                "4:30 PM",
                "5:00 PM",
                "5:30 PM",
                "6:00 PM",
                "6:30 PM",
                "7:00 PM",
                "7:30 PM",
                "8:00 PM",
                "8:30 PM",
                "9:00 PM",
                "9:30 PM",
                "10:00 PM",
                "10:30 PM",
                "11:00 PM",
                "11:30 PM",
                "12:00 AM",
                "12:30 AM",
                "1:00 AM",
                "1:30 AM",
                "2:00 AM",
                "2:30 AM",
                "3:00 AM",
                "3:30 AM",
                "4:00 AM",
                "4:30 AM",
                "5:00 AM",
                "5:30 AM",
                "6:00 AM",
                "6:30 AM",
                "7:00 AM",
                "7:30 AM",
                "8:00 AM",
                "8:30 AM",
                "9:00 AM",
                "9:30 AM"
        );
        return 0;
    }

    void placeOrder_button(Customer customer){
        Button placeOrderButton = new Button("Place Order");
    }

    void order_Status_GUI(Customer customer){
        HBox orderStatusGUI = new HBox();
    }
}
