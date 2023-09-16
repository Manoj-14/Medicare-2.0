package com.project.medicare.dto;

import com.project.medicare.entity.Address;
import com.project.medicare.entity.Cart;
import com.project.medicare.entity.Purchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    int user_id;
    String name;
    String email;
    String password;
    long phone;
    List<Cart> cart = new ArrayList<>();
    List<Purchase> purchases = new ArrayList<>();
    Address address;
}
