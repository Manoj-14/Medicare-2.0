package com.project.medicare.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id ;

    @OneToOne()
    Medicine medicines;
    int quantity;

    double amount;
    public Cart() {

    }

    public Cart(Medicine medicines, int quantity,double amount) {
        super();
        this.medicines = medicines;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Medicine getMedicines() {
        return medicines;
    }

    public void setMedicines(Medicine medicines) {
        this.medicines = medicines;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", medicines=" + medicines +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}

