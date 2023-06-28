package com.example.demo.exception;

public class MedicineInActiveException extends RuntimeException{
    public MedicineInActiveException(String message){
        super(message);
    }
}
