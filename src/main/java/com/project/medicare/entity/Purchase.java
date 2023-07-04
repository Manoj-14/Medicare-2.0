package com.project.medicare.entity;
import jakarta.persistence.*;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Medicine medicine;

    int quantity;

    double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment method")
    PaymentMethods paymentMethod;

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Purchase() {

    }
    public Purchase(Medicine medicine, int quantity, double totalAmount) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public Purchase(Medicine medicine, int quantity, double totalAmount, PaymentMethods paymentMethod) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
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
        return "Purchase{" +
                "id=" + id +
                ", medicine=" + medicine +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}

