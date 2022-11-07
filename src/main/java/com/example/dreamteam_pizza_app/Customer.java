package com.example.dreamteam_pizza_app;

public class Customer {

    int asuID;
    String emailAddress;
    PizzaOrder Pizza;

    boolean orderFinished(){
        return true;
    }

    boolean validateStudent_Email(){
        if (emailAddress.contains("@") && emailAddress.contains(".edu")) {
            return true;
        }else {
            return false;
        }
    }

    boolean validateStudentASU_ID(){
        if (asuID > 100000000 && asuID < 999999999) {
            return true;
        }else {
            return false;
        }
    }
}
