package com.project.medicare.repository;

import com.project.medicare.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
}
