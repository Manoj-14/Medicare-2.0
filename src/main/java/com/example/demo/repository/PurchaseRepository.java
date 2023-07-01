package com.example.demo.repository;

import com.example.demo.entity.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer> {
}
