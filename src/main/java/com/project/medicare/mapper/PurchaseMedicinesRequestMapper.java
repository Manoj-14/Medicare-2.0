package com.project.medicare.mapper;


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
