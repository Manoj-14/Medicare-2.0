package com.example.demo.mapper;

public class PurchaseMapper{
    int medicineId;
    int quantity;
    double totalAmount;

    public PurchaseMapper(int medicineId, int quantity, double totalAmount) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "purchaseMapper{" +
                "medicineID=" + medicineId +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
