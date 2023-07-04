package com.project.medicare.repository;

import com.project.medicare.entity.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer> {
}
