package com.example.demo.service;

import com.example.demo.entity.Medicine;
import com.example.demo.exception.EntityCreatingException;
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
    void update(Medicine medicine) throws EntityCreatingException;

    Medicine getMedicine(int id) throws EntityNotFoundException;

    List<Medicine> getMedicines();
}
