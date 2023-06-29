package com.example.demo.exception;

public class MedicineNotFoundException extends Exception{

    public MedicineNotFoundException(){
        super("Medicine not found");
    }
}
