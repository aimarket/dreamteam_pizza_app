package com.example.dreamteam_pizza_app;

import java.util.ArrayList;

public class PizzaOrder {
    String pizzaType;
    private ArrayList<String> pizzaToppings = new ArrayList<String>();
    String pizzaPickupTime;
    String pizzaPickupTimeAmOrPm;
    String orderStatus = "";

    public PizzaOrder(String pizzaType, ArrayList<String> toppings, String pickupTime, String amOrPm)
    {
        this.pizzaType = pizzaType;
        for(int i = 0; i < toppings.size(); i++)
        {
            pizzaToppings.add(toppings.get(i));
        }
        pizzaPickupTime = pickupTime;
        pizzaPickupTimeAmOrPm = amOrPm;
        orderStatus = "Ready to Cook";
    }

    public void changeOrderStatus(String newStatus)
    {
        orderStatus = newStatus;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }
    public String getPizzaPickupTime()
    {
        return pizzaPickupTime;
    }
    public String getPizzaPickupTimeAmOrPm()
    {
        return pizzaPickupTimeAmOrPm;
    }

    public ArrayList<String> getPizzaToppings()
    {
        return pizzaToppings;
    }

    public String getPizzaType()
    {
        return pizzaType;
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
