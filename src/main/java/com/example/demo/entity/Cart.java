package com.example.demo.entity;

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

    @OneToOne(cascade = CascadeType.ALL)
    Medicine medicines;
    int quantity;

    public Cart() {

    }

    public Cart(Medicine medicines, int quantity) {
        super();
        this.medicines = medicines;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", medicines=" + medicines +
                ", quantity=" + quantity +
                '}';
    }
}

