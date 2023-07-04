package com.project.medicare.exception;

public class MedicineNotFoundException extends Exception{

    public MedicineNotFoundException(){
        super("Medicine not found");
    }
}
