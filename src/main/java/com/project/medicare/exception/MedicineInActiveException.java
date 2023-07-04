package com.project.medicare.exception;

public class MedicineInActiveException extends RuntimeException{
    public MedicineInActiveException(String message){
        super(message);
    }
}
