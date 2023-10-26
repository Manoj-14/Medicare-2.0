package com.project.medicare.mapper;


import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PurchaseMedicinesRequestMapper {
    List<PurchaseMapper> purchases;

    public PurchaseMedicinesRequestMapper(List<PurchaseMapper> purchases){
        this.purchases = purchases;
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
                "purchases=" + purchases +
                '}';
    }
}
