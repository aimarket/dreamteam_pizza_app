package com.example.dreamteam_pizza_app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class PizzaApp extends Application {

    ArrayList<Customer> customerList = new ArrayList<Customer>();
    String currentPizzaType = "";
    ArrayList<String> toppings = new ArrayList<String>();

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

        //Customer actions

        ToggleGroup pizzaTypeGroup = new ToggleGroup();
        controller.rbutton_pepperoni.setToggleGroup(pizzaTypeGroup);
        controller.rbutton_cheese.setToggleGroup(pizzaTypeGroup);
        controller.rbutton_Vegetable.setToggleGroup(pizzaTypeGroup);

        ObservableList<String> timeList = FXCollections.observableArrayList();
        Collections.addAll(timeList, "12:00", "12:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00",
                "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30");

        ObservableList<String> amPmList = FXCollections.observableArrayList();
        Collections.addAll(amPmList, "AM", "PM");

        controller.combobox_time.setItems(timeList);
        controller.combobox_ampm.getItems().addAll("AM", "PM");
                //.setItems(amPmList);

        //Customer presses Add to Order
        controller.button_add_to_order.setOnAction(event ->
        {
            toppings.clear();
            currentPizzaType = "";

           if(pizzaTypeGroup.getSelectedToggle() == controller.rbutton_pepperoni)
           {
               currentPizzaType = "Pepperoni";
           }
           else if(pizzaTypeGroup.getSelectedToggle() == controller.rbutton_Vegetable)
           {
               currentPizzaType = "Vegetable";
           }
           else if(pizzaTypeGroup.getSelectedToggle() == controller.rbutton_cheese)
           {
               currentPizzaType = "Cheese";
           }

           if(controller.checkbox_extra_cheese.isSelected())
           {
               toppings.add("Extra Cheese");
           }
           if(controller.checkbox_mushroom.isSelected())
           {
               toppings.add("Mushroom");
           }
           if(controller.checkbox_olives.isSelected())
           {
               toppings.add("Olives");
           }
           if(controller.checkbox_onion.isSelected())
           {
               toppings.add("Onion");
           }

           String orderDisplay = currentPizzaType + " Pizza \n";
           for(int i = 0; i < toppings.size(); i++)
           {
               orderDisplay += "    " + toppings.get(i) + "\n";
           }
            controller.textarea_order.setText(orderDisplay);
        });

                //Customer presses place order
        controller.button_placeorder.setOnAction(event ->
        {
            int existingCustomerMatchingPosition = -1;
            int enteredID = Integer.parseInt(controller.textfield_asurite.getText());
            for(int i = 0; i < customerList.size(); i++)
            {
                if(customerList.get(i).getAsuID() == enteredID)
                {
                    existingCustomerMatchingPosition = i;
                }
            }
            if(existingCustomerMatchingPosition == -1)
            {
                Customer customer = new Customer(enteredID);

                if(customer.validateStudentASU_ID())
                {
                    String amOrPm = (String) controller.combobox_ampm.getValue();
                    customerList.add(customer);
                    PizzaOrder pizzaOrder = new PizzaOrder(currentPizzaType, toppings, (String) controller.combobox_time.getValue(), (String) controller.combobox_ampm.getValue());
                    customer.addPizzaOrder(pizzaOrder);
                    controller.textarea_order.setText("Order Placed has been placed for " + enteredID + ".");
                }
                else
                {
                    controller.textarea_order.setText("ASURITE ID is invalid");
                }
            }
            else
            {
                String amOrPm = (String) controller.combobox_ampm.getValue();
                PizzaOrder pizzaOrder = new PizzaOrder(currentPizzaType, toppings, (String) controller.combobox_time.getValue(), (String) controller.combobox_ampm.getValue());
                customerList.get(existingCustomerMatchingPosition).addPizzaOrder(pizzaOrder);
                controller.textarea_order.setText("Order Placed has been placed for " + enteredID + ".");
            }
        });

        controller.button_status.setOnAction(event ->
        {
            int existingCustomerMatchingPosition = -1;
            int enteredID = Integer.parseInt(controller.textfield_asurite_status.getText());
            for(int i = 0; i < customerList.size(); i++)
            {
                if(customerList.get(i).getAsuID() == enteredID)
                {
                    existingCustomerMatchingPosition = i;
                }
            }
            if(existingCustomerMatchingPosition != -1)
            {
                ArrayList<PizzaOrder> customersOrders = customerList.get(existingCustomerMatchingPosition).getPizzaOrderList();
                String orderStatusResponse = "Order History: \n";
                for(int i = 0; i < customersOrders.size(); i++)
                {
                    orderStatusResponse += customersOrders.get(i).pizzaType + "\n";
                    for(int j = 0; j < customersOrders.get(i).pizzaToppings.size(); j++)
                    {
                        orderStatusResponse += "    " + customersOrders.get(i).pizzaToppings.get(j) + "\n";
                    }
                    orderStatusResponse += "Order Status: " + customersOrders.get(i).getOrderStatus() + "\n";
                }
                controller.textarea_order.setText(orderStatusResponse);
            }
            else
            {
                controller.textarea_order.setText("No Orders accosiated with the\nentered ASURITE ID.");
            }
        });

    }
}
