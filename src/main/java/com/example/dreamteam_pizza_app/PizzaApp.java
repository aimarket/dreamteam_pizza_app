package com.example.dreamteam_pizza_app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
        FXMLLoader fxmlLoaderCustomer = new FXMLLoader(PizzaApp.class.getResource("customer_dashboard.fxml"));
        FXMLLoader fxmlLoaderCheff = new FXMLLoader(PizzaApp.class.getResource("cheff_dashboard.fxml"));

        Scene customerScene = new Scene(fxmlLoaderCustomer.load(), 600, 400);
        Scene cheffScene = new Scene(fxmlLoaderCheff.load(), 600, 400);

        //display the scene customer
        primaryStage.setTitle("DreamTeam Pizza App");
        primaryStage.setScene(customerScene);
        primaryStage.show();

        CustomerDashboardController customerController = fxmlLoaderCustomer.getController();//this is the customerController
        CheffDashboardController cheffController = fxmlLoaderCheff.getController();//this is the cheff controller

        customerController.testingChange();//this is the method in the customerController example only

        //#############################################################################################################
        // Customer actions

        ToggleGroup pizzaTypeGroup = new ToggleGroup();
        customerController.rbutton_pepperoni.setToggleGroup(pizzaTypeGroup);
        customerController.rbutton_cheese.setToggleGroup(pizzaTypeGroup);
        customerController.rbutton_Vegetable.setToggleGroup(pizzaTypeGroup);

        ObservableList<String> timeList = FXCollections.observableArrayList();
        Collections.addAll(timeList, "12:00", "12:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00",
                "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30");

        ObservableList<String> amPmList = FXCollections.observableArrayList();
        Collections.addAll(amPmList, "AM", "PM");

        customerController.combobox_time.setItems(timeList);
        customerController.combobox_ampm.getItems().addAll("AM", "PM");
                //.setItems(amPmList);

        //Customer and staff scene toggle button
        customerController.button_customer.setOnAction(event ->
        {
            primaryStage.setScene(cheffScene);
            primaryStage.show();
        });

        //Customer presses Add to Order
        customerController.button_add_to_order.setOnAction(event ->
        {
            toppings.clear();
            currentPizzaType = "";

           if(pizzaTypeGroup.getSelectedToggle() == customerController.rbutton_pepperoni)
           {
               currentPizzaType = "Pepperoni";
           }
           else if(pizzaTypeGroup.getSelectedToggle() == customerController.rbutton_Vegetable)
           {
               currentPizzaType = "Vegetable";
           }
           else if(pizzaTypeGroup.getSelectedToggle() == customerController.rbutton_cheese)
           {
               currentPizzaType = "Cheese";
           }

           if(customerController.checkbox_extra_cheese.isSelected())
           {
               toppings.add("Extra Cheese");
           }
           if(customerController.checkbox_mushroom.isSelected())
           {
               toppings.add("Mushroom");
           }
           if(customerController.checkbox_olives.isSelected())
           {
               toppings.add("Olives");
           }
           if(customerController.checkbox_onion.isSelected())
           {
               toppings.add("Onion");
           }

           String orderDisplay = currentPizzaType + " Pizza \n";
           for(int i = 0; i < toppings.size(); i++)
           {
               orderDisplay += "    " + toppings.get(i) + "\n";
           }
            customerController.textarea_order.setText(orderDisplay);
        });

                //Customer presses place order
        customerController.button_placeorder.setOnAction(event ->
        {
            int existingCustomerMatchingPosition = -1;
            int enteredID = Integer.parseInt(customerController.textfield_asurite.getText());
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
                    String amOrPm = (String) customerController.combobox_ampm.getValue();
                    customerList.add(customer);
                    PizzaOrder pizzaOrder = new PizzaOrder(currentPizzaType, toppings, (String) customerController.combobox_time.getValue(), (String) customerController.combobox_ampm.getValue());
                    customer.addPizzaOrder(pizzaOrder);
                    customerController.textarea_order.setText("Order Placed has been placed for " + enteredID + ".");
                }
                else
                {
                    customerController.textarea_order.setText("ASURITE ID is invalid");
                }
            }
            else
            {
                String amOrPm = (String) customerController.combobox_ampm.getValue();
                PizzaOrder pizzaOrder = new PizzaOrder(currentPizzaType, toppings, (String) customerController.combobox_time.getValue(), (String) customerController.combobox_ampm.getValue());
                customerList.get(existingCustomerMatchingPosition).addPizzaOrder(pizzaOrder);
                customerController.textarea_order.setText("Order Placed has been placed for " + enteredID + ".");
            }
        });

        customerController.button_status.setOnAction(event ->
        {
            int existingCustomerMatchingPosition = -1;
            int enteredID = Integer.parseInt(customerController.textfield_asurite_status.getText());
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
                customerController.textarea_order.setText(orderStatusResponse);
            }
            else
            {
                customerController.textarea_order.setText("No Orders accosiated with the\nentered ASURITE ID.");
            }
        });

        //#########################################################################################################
        //cheff actions
        //Customer and staff scene toggle button

        cheffController.button_staff.setOnAction(event ->
        {

            primaryStage.setScene(customerScene);
            primaryStage.show();
        });

    }
}
