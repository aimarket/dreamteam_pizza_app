package com.example.dreamteam_pizza_app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class PizzaApp extends Application {

    ArrayList<Customer> customerList = new ArrayList<Customer>();
    String currentPizzaType = "";
    ArrayList<String> toppings = new ArrayList<String>();



    int outstandingOrders = 0;
    int currentOrderPage = 0;

    String orderHistory = "";

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


        //#############################################################################################################
        int unfinishedOrders = 0;
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

            if (pizzaTypeGroup.getSelectedToggle() == customerController.rbutton_pepperoni) {
                currentPizzaType = "Pepperoni";
            } else if (pizzaTypeGroup.getSelectedToggle() == customerController.rbutton_Vegetable) {
                currentPizzaType = "Vegetable";
            } else if (pizzaTypeGroup.getSelectedToggle() == customerController.rbutton_cheese) {
                currentPizzaType = "Cheese";
            }

            if (customerController.checkbox_extra_cheese.isSelected()) {
                toppings.add("Extra Cheese");
            }
            if (customerController.checkbox_mushroom.isSelected()) {
                toppings.add("Mushroom");
            }
            if (customerController.checkbox_olives.isSelected()) {
                toppings.add("Olives");
            }
            if (customerController.checkbox_onion.isSelected()) {
                toppings.add("Onion");
            }

            String orderDisplay = currentPizzaType + " Pizza \n";
            for (int i = 0; i < toppings.size(); i++) {
                orderDisplay += "    " + toppings.get(i) + "\n";
            }
            customerController.textarea_order.setText(orderDisplay);
        });

        //Customer presses place order
        customerController.button_placeorder.setOnAction(event ->
        {
            int existingCustomerMatchingPosition = -1;
            int enteredID = 0;
            try {
                enteredID = Integer.parseInt(customerController.textfield_asurite.getText());
            }catch (NumberFormatException e){
                customerController.textfield_asurite.setText("Invalid ID");
            }

            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getAsuID() == enteredID) {
                    existingCustomerMatchingPosition = i;
                }
            }
            if (existingCustomerMatchingPosition == -1) {
                Customer customer = new Customer(enteredID);

                if (customer.validateStudentASU_ID()) {
                    String amOrPm = (String) customerController.combobox_ampm.getValue();
                    customerList.add(customer);
                    PizzaOrder pizzaOrder = new PizzaOrder(currentPizzaType, toppings, (String) customerController.combobox_time.getValue(), (String) customerController.combobox_ampm.getValue());
                    customer.addPizzaOrder(pizzaOrder);
                    customerController.textarea_order.setText("Order Placed has been placed for " + enteredID + ".");
                    updateOrderHistory(cheffController, "ORDER ACCEPTED: " + enteredID + " - " + customerController.combobox_time.getValue() + " " + customerController.combobox_ampm.getValue());
                    refreshOrderList(cheffController);
                } else {
                    customerController.textarea_order.setText("ASURITE ID is invalid");
                }
            } else {
                String amOrPm = (String) customerController.combobox_ampm.getValue();
                PizzaOrder pizzaOrder = new PizzaOrder(currentPizzaType, toppings, (String) customerController.combobox_time.getValue(), (String) customerController.combobox_ampm.getValue());
                customerList.get(existingCustomerMatchingPosition).addPizzaOrder(pizzaOrder);
                customerController.textarea_order.setText("Order Placed has been placed for " + enteredID + ".");
                updateOrderHistory(cheffController, "ORDER ACCEPTED: " + enteredID + " - " + customerController.combobox_time.getValue() + " " + customerController.combobox_ampm.getValue());
                refreshOrderList(cheffController);
            }
        });

        customerController.button_status.setOnAction(event ->
        {
            int existingCustomerMatchingPosition = -1;
            int enteredID = Integer.parseInt(customerController.textfield_asurite_status.getText());
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getAsuID() == enteredID) {
                    existingCustomerMatchingPosition = i;
                }
            }
            if (existingCustomerMatchingPosition != -1) {
                ArrayList<PizzaOrder> customersOrders = customerList.get(existingCustomerMatchingPosition).getPizzaOrderList();
                String orderStatusResponse = "Order History: \n";
                for (int i = 0; i < customersOrders.size(); i++) {
                    orderStatusResponse += customersOrders.get(i).pizzaType + "\n";
                    for (int j = 0; j < customersOrders.get(i).getPizzaToppings().size(); j++) {
                        orderStatusResponse += "    " + customersOrders.get(i).getPizzaToppings().get(j) + "\n";
                    }
                    orderStatusResponse += "Order Status: " + customersOrders.get(i).getOrderStatus() + "\n";
                }
                customerController.textarea_order.setText(orderStatusResponse);
            } else {
                customerController.textarea_order.setText("No Orders associated with the\nentered ASURITE ID.");
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

        cheffController.button_order_status_change_one.setOnAction(event ->
        {
                cheffController.label_order_status_one.setText((String) cheffController.combobox_order_select_status_one.getValue());
            int targetIndex = currentOrderPage * 4;
            int currentIndex = 0;
            String tempStatus;


            for (int i = 0; i < customerList.size(); i++) {
                    for (int j = 0; j < customerList.get(i).getPizzaOrderList().size(); j++) {
                        if(currentIndex == targetIndex)
                        {
                            customerList.get(i).getPizzaOrderList().get(j).changeOrderStatus((String) cheffController.combobox_order_select_status_one.getValue());
                            if(outstandingOrders - 1 == currentIndex)
                            {

                                tempStatus = cheffController.combobox_order_select_status_one.getValue().toString();
                                if(tempStatus.equals("Delete"))
                                {
                                    customerController.text_status.setText("no order to display");
                                }else {
                                    customerController.text_status.setText(tempStatus);
                                }

                                if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Ready to Cook")
                                {
                                    customerController.progressbar_status.setProgress(0.33);
                                    updateOrderHistory(cheffController, "READY TO COOK: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                }
                                else if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Cooking")
                                {
                                    customerController.progressbar_status.setProgress(0.66);
                                    updateOrderHistory(cheffController, "COOKING: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                }
                                else if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Done")
                                {
                                    customerController.progressbar_status.setProgress(1);
                                    updateOrderHistory(cheffController, "ORDER FINISHED AND EMAIL SENT: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                    System.out.println("Email sent to " + customerList.get(i).getAsuID()+"@asu.edu");
                                }
                            }
                            if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Delete")
                            {
                                updateOrderHistory(cheffController, "ORDER DELETED: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                customerList.get(i).getPizzaOrderList().remove(j);
                                outstandingOrders--;
                                refreshOrderList(cheffController);
                                break;
                            }
                        }
                        currentIndex++;

                    }
                }

        });

        cheffController.button_order_status_change_two.setOnAction(event ->
        {
            cheffController.label_order_status_two.setText((String) cheffController.combobox_order_select_status_two.getValue());
            int targetIndex = currentOrderPage * 4 + 1;
            int currentIndex = 0;
            String tempStatus;


            for (int i = 0; i < customerList.size(); i++) {
                for (int j = 0; j < customerList.get(i).getNumerOfOrders(); j++) {
                    if(currentIndex == targetIndex)
                    {
                        customerList.get(i).getPizzaOrderList().get(j).changeOrderStatus((String) cheffController.combobox_order_select_status_two.getValue());
                        tempStatus = cheffController.combobox_order_select_status_two.getValue().toString();
                        if(tempStatus.equals("Delete"))
                        {
                            customerController.text_status.setText("no order to display");
                        }else {
                            customerController.text_status.setText(tempStatus);
                        }

                        if(outstandingOrders - 1 == currentIndex) {
                            customerController.text_status.setText((String) cheffController.combobox_order_select_status_two.getValue());
                            if (customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Ready to Cook") {
                                customerController.progressbar_status.setProgress(0.33);
                                updateOrderHistory(cheffController, "READY TO COOK: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            } else if (customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Cooking") {
                                customerController.progressbar_status.setProgress(0.66);
                                updateOrderHistory(cheffController, "COOKING: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            } else if (customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Done") {
                                customerController.progressbar_status.setProgress(1);
                                updateOrderHistory(cheffController, "ORDER FINISHED AND EMAIL SENT: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                System.out.println("Email sent to " + customerList.get(i).getAsuID() + "@asu.edu");
                            }

                        }
                        if (customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Delete") {
                            updateOrderHistory(cheffController, "ORDER DELETED: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            customerList.get(i).getPizzaOrderList().remove(j);
                            outstandingOrders--;

                            refreshOrderList(cheffController);
                            break;
                        }
                    }
                    currentIndex++;

                }
            }

        });

        cheffController.button_order_status_change_three.setOnAction(event ->
        {
            cheffController.label_order_status_three.setText((String) cheffController.combobox_order_select_status_three.getValue());
            int targetIndex = currentOrderPage * 4 + 2;
            int currentIndex = 0;
            String tempStatus;


            for (int i = 0; i < customerList.size(); i++) {
                for (int j = 0; j < customerList.get(i).getNumerOfOrders(); j++) {
                    if(currentIndex == targetIndex)
                    {
                        customerList.get(i).getPizzaOrderList().get(j).changeOrderStatus((String) cheffController.combobox_order_select_status_three.getValue());
                        tempStatus = cheffController.combobox_order_select_status_three.getValue().toString();
                        if(tempStatus.equals("Delete"))
                        {
                            customerController.text_status.setText("no order to display");
                        }else {
                            customerController.text_status.setText(tempStatus);
                        }
                        if(outstandingOrders - 1 == currentIndex)
                        {
                            customerController.text_status.setText((String) cheffController.combobox_order_select_status_three.getValue());
                            if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Ready to Cook")
                            {
                                customerController.progressbar_status.setProgress(0.33);
                                updateOrderHistory(cheffController, "READY TO COOK: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            }
                            else if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Cooking")
                            {
                                customerController.progressbar_status.setProgress(0.66);
                                updateOrderHistory(cheffController, "COOKING: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            }
                            else if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Done")
                            {
                                customerController.progressbar_status.setProgress(1);
                                updateOrderHistory(cheffController, "ORDER FINISHED AND EMAIL SENT: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                System.out.println("Email sent to " + customerList.get(i).getAsuID()+"@asu.edu");
                            }
                        }
                        if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Delete")
                        {
                            updateOrderHistory(cheffController, "ORDER DELETED: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            customerList.get(i).getPizzaOrderList().remove(j);
                            outstandingOrders--;
                            refreshOrderList(cheffController);
                            break;
                        }
                    }
                    currentIndex++;

                }
            }

        });

        cheffController.button_order_status_change_four.setOnAction(event ->
        {
            cheffController.label_order_status_four.setText((String) cheffController.combobox_order_select_status_four.getValue());
            int targetIndex = currentOrderPage * 4 + 3;
            int currentIndex = 0;


            for (int i = 0; i < customerList.size(); i++) {
                for (int j = 0; j < customerList.get(i).getNumerOfOrders(); j++) {
                    if(currentIndex == targetIndex)
                    {
                        customerList.get(i).getPizzaOrderList().get(j).changeOrderStatus((String) cheffController.combobox_order_select_status_four.getValue());
                        String tempStatus = cheffController.combobox_order_select_status_four.getValue().toString();
                        if(tempStatus.equals("Delete"))
                        {
                            customerController.text_status.setText("no order to display");
                        }else {
                            customerController.text_status.setText(tempStatus);
                        }
                        if(outstandingOrders - 1 == currentIndex)
                        {
                            customerController.text_status.setText((String) cheffController.combobox_order_select_status_four.getValue());
                            if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Ready to Cook")
                            {
                                customerController.progressbar_status.setProgress(0.33);
                                updateOrderHistory(cheffController, "READY TO COOK: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            }
                            else if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Cooking")
                            {
                                customerController.progressbar_status.setProgress(0.66);
                                updateOrderHistory(cheffController, "COOKING: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            }
                            else if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Done")
                            {
                                customerController.progressbar_status.setProgress(1);
                                updateOrderHistory(cheffController, "ORDER FINISHED AND EMAIL SENT: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                                System.out.println("Email sent to " + customerList.get(i).getAsuID()+"@asu.edu");
                            }
                        }
                        if(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus() == "Delete")
                        {
                            updateOrderHistory(cheffController, "ORDER DELETED: " + customerList.get(i).getAsuID() + " - " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                            customerList.get(i).getPizzaOrderList().remove(j);
                            outstandingOrders--;
                            refreshOrderList(cheffController);
                            break;
                        }
                    }
                    currentIndex++;

                }
            }

        });


        //Staff GUI
        ObservableList<String> statusList = FXCollections.observableArrayList();
        Collections.addAll(statusList, "Ready to Cook", "Cooking", "Done", "Delete");

        cheffController.pane_pizza_order_one.setVisible(false);
        cheffController.pane_pizza_order_two.setVisible(false);
        cheffController.pane_pizza_order_three.setVisible(false);
        cheffController.pane_pizza_order_four.setVisible(false);

        cheffController.combobox_order_select_status_one.setItems(statusList);
        cheffController.combobox_order_select_status_two.setItems(statusList);
        cheffController.combobox_order_select_status_three.setItems(statusList);
        cheffController.combobox_order_select_status_four.setItems(statusList);

        cheffController.button_orders_left.setOnAction(event ->
        {
            if(currentOrderPage > 0)
            {
                currentOrderPage--;
                refreshOrderList(cheffController);
            }
        });

        cheffController.button_orders_right.setOnAction(event ->
        {
            if(currentOrderPage * 4 < outstandingOrders)
            {
                currentOrderPage++;
                refreshOrderList(cheffController);
            }
        });


    }


    private void refreshOrderList(CheffDashboardController cheffController) {

        countOutstandingOrders();
        int currentIndex = 0;

        cheffController.pane_pizza_order_one.setVisible(false);
        cheffController.pane_pizza_order_two.setVisible(false);
        cheffController.pane_pizza_order_three.setVisible(false);
        cheffController.pane_pizza_order_four.setVisible(false);

        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getPizzaOrderList().size() == 0) {
                customerList.remove(i);
                i--;
            } else {
                int startingPos = currentOrderPage * 4;

                for (int j = 0; j < customerList.get(i).getPizzaOrderList().size(); j++) {
                    if (currentIndex == startingPos)
                    {
                        cheffController.pane_pizza_order_one.setVisible(true);
                        cheffController.label_order_customer_name_one.setText("ID: " + customerList.get(i).getAsuID());
                        cheffController.label_order_status_one.setText(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus());
                        cheffController.label_order_time_one.setText(customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                        cheffController.label_order_pizza_type_one.setText(getOrderAbbreviation(customerList.get(i).getPizzaOrderList().get(j)));
                    }
                    else if (currentIndex == startingPos + 1)
                    {
                        cheffController.pane_pizza_order_two.setVisible(true);
                        cheffController.label_order_customer_name_two.setText("ID: " + customerList.get(i).getAsuID());
                        cheffController.label_order_status_two.setText(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus());
                        cheffController.label_order_time_two.setText(customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                        cheffController.label_order_pizza_type_two.setText(getOrderAbbreviation(customerList.get(i).getPizzaOrderList().get(j)));
                    }
                    else if (currentIndex == startingPos + 2)
                    {
                        cheffController.pane_pizza_order_three.setVisible(true);
                        cheffController.label_order_customer_name_three.setText("ID: " + customerList.get(i).getAsuID());
                        cheffController.label_order_status_three.setText(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus());
                        cheffController.label_order_time_three.setText(customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                        cheffController.label_order_pizza_type_three.setText(getOrderAbbreviation(customerList.get(i).getPizzaOrderList().get(j)));
                    }
                    else if (currentIndex == startingPos + 3)
                    {
                        cheffController.pane_pizza_order_four.setVisible(true);
                        cheffController.label_order_customer_name_four.setText("ID: " + customerList.get(i).getAsuID());
                        cheffController.label_order_status_four.setText(customerList.get(i).getPizzaOrderList().get(j).getOrderStatus());
                        cheffController.label_order_time_four.setText(customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTime() + " " + customerList.get(i).getPizzaOrderList().get(j).getPizzaPickupTimeAmOrPm());
                        cheffController.label_order_pizza_type_four.setText(getOrderAbbreviation(customerList.get(i).getPizzaOrderList().get(j)));
                    }
                    currentIndex++;

                }
            }
        }
    }

    private void countOutstandingOrders() {
        outstandingOrders = 0;
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getPizzaOrderList().size() == 0) {
                customerList.remove(i);
                i--;
            } else {
                int startingPos = currentOrderPage * 4;

                for (int j = 0; j < customerList.get(i).getPizzaOrderList().size(); j++) {
                    outstandingOrders++;
                }
            }
        }
        if(outstandingOrders == 0)
        {
            currentOrderPage = 0;
        }
        else if(currentOrderPage * 4 > outstandingOrders)
        {
            currentOrderPage--;
        }
    }

    private String getOrderAbbreviation(PizzaOrder pizzaOrder)
    {
        String orderAbreviation = "";
        switch(pizzaOrder.getPizzaType())
        {
            case "Pepperoni": orderAbreviation += "PEP - "; break;
            case "Vegetable": orderAbreviation += "VEG - "; break;
            case "Cheese": orderAbreviation += "CHE - "; break;
        }
        if(pizzaOrder.getPizzaToppings().size() == 4)
        {
            orderAbreviation += "ALL";
        }
        else
        {
            for(int i = 0; i < pizzaOrder.getPizzaToppings().size(); i++)
            {
                if(pizzaOrder.getPizzaToppings().get(i) == "Extra Cheese")
                {
                    orderAbreviation += "ECH, ";
                }
                else if(pizzaOrder.getPizzaToppings().get(i) == "Mushroom")
                {
                    orderAbreviation += "MSH, ";
                }
                else if(pizzaOrder.getPizzaToppings().get(i) == "Olives")
                {
                    orderAbreviation += "OL, ";
                }
                else if(pizzaOrder.getPizzaToppings().get(i) == "Onion")
                {
                    orderAbreviation += "ONI";
                }
            }
        }
        return orderAbreviation;
    }

    private void updateOrderHistory(CheffDashboardController cheffController, String addLine) {
        orderHistory += "\n" + addLine;
        try {
            FileWriter writer = new FileWriter("src/main/java/com/example/dreamteam_pizza_app/CustomerFiles/OrderHistory.txt");
            writer.write(orderHistory);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        cheffController.textarea_order_history.setText(orderHistory);
    }

}
