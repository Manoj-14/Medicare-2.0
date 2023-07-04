package com.project.medicare.service;

import com.project.medicare.entity.Medicine;
import com.project.medicare.exception.EntityCreatingException;
import com.project.medicare.exception.MedicineNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MedicineService {
    @Transactional
    Medicine add(Medicine medicine) throws EntityCreatingException;

    @Transactional
    void remove(int id) throws EntityNotFoundException;

    @Transactional
    void enableOrDisable(int id) throws EntityNotFoundException;

    @Transactional
    void update(Medicine medicine) throws EntityCreatingException,MedicineNotFoundException;

    Medicine getMedicine(int id) throws MedicineNotFoundException;

    List<Medicine> getMedicines();
}
