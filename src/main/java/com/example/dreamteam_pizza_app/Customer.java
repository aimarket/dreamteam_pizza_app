package com.example.dreamteam_pizza_app;

import java.util.ArrayList;

public class Customer {

    private int asuID;
    private ArrayList<PizzaOrder> pizzaOrderList = new ArrayList<PizzaOrder>();

    public Customer(int asuIDNumber)
    {
        asuID = asuIDNumber;
    }

    public ArrayList<PizzaOrder> getPizzaOrderList()
    {
        return pizzaOrderList;
    }

    public void addPizzaOrder(PizzaOrder pizzaOrder)
    {
        pizzaOrderList.add(pizzaOrder);
    }

    public int getAsuID() {
        return asuID;
    }

    boolean orderFinished(){
        return true;
    }



    boolean validateStudentASU_ID(){
        if (asuID > 100000000 && asuID < 2147483646) {
            return true;
        }else {
            return false;
        }
    }
}
