package com.example.demo.mapper;


import com.example.demo.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class PurchaseMedicinesRequestMapper {

    String email;
    List<PurchaseMapper> purchases;

    public PurchaseMedicinesRequestMapper(String email, List<PurchaseMapper> purchases) {
        this.email = email;
        this.purchases = purchases;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PurchaseMapper> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseMapper> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "PurchaseMedicinesRequestMapper{" +
                "email='" + email + '\'' +
                ", purchases=" + purchases +
                '}';
    }
}
