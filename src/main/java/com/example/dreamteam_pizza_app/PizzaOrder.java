package com.example.dreamteam_pizza_app;

import java.util.ArrayList;

public class PizzaOrder {
    String pizzaType;
    ArrayList<String> pizzaToppings;
    String pizzaPickupTime;
    String pizzaPickupTimeAmOrPm;
    String orderStatus = "";

    public PizzaOrder(String pizzaType, ArrayList<String> toppings, String pickupTime, String amOrPm)
    {
        this.pizzaType = pizzaType;
        this.pizzaToppings = toppings;
        this.pizzaPickupTime = pickupTime;
        this.pizzaPickupTimeAmOrPm = amOrPm;
        this.orderStatus = "Ready to Cook";
    }
    boolean changeOrderStatus(){
        return true;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    String orderHistory(){
        return "";
    }

    boolean orderCompletion(){
        return true;
    }

    String emailDelivery(){
        return "";
    }

    boolean orderDeletion(){
        return true;
    }

}
