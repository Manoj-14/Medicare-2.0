package com.project.medicare.repository;

import com.project.medicare.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findById(int id);
}
