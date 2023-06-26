package com.example.demo.service;

import com.example.demo.entity.Medicine;
import com.example.demo.exception.EntityCreatingException;
import com.example.demo.repository.MedicineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineServiceImpl {

    @Autowired
    MedicineRepository medicineRepository;

    public Medicine add(Medicine medicine) throws EntityCreatingException {
        try {
            medicineRepository.save(medicine);
            return medicine;
        }catch (Exception ec){
            throw new EntityCreatingException();
        }
    }

    public void remove(int id) throws EntityNotFoundException{
        Medicine medicine = medicineRepository.findById(id);
        if(medicine != null){
            medicineRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException();
        }
    }



}
