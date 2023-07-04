package com.project.medicare.repository;

import com.project.medicare.entity.Medicine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine,Integer> {
    Medicine findById(int id);

}
