package com.project.medicare.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int user_id;
    String name;
    String email;
    @Column(length = 10)
    long phone;
    @Embedded
    Address address;
    byte[] storedSalt;
    byte[] password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Cart> cart = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Purchase> purchases = new ArrayList<>();

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
        this.cart = new ArrayList<>();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public byte[] getStoredSalt() {
        return storedSalt;
    }

    public void setStoredSalt(byte[] storedSalt) {
        this.storedSalt = storedSalt;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", storedSalt=" + Arrays.toString(storedSalt) +
                ", password=" + Arrays.toString(password) +
                ", cart=" + cart +
                ", purchases=" + purchases +
                '}';
    }
}
