package com.example.demo.service;

import com.example.demo.entity.Medicine;
import com.example.demo.exception.EntityCreatingException;
import com.example.demo.exception.MedicineNotFoundException;
import com.example.demo.repository.MedicineRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    MedicineRepository medicineRepository;
    @Override
    @Transactional
    public Medicine add(Medicine medicine) throws EntityCreatingException {
        try {
            medicineRepository.save(medicine);
            return medicine;
        }catch (Exception ec){
            throw new EntityCreatingException();
        }
    }
    @Override
    @Transactional
    public void remove(int id) throws EntityNotFoundException{
        Medicine medicine = medicineRepository.findById(id);
        if(medicine != null){
            medicineRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException();
        }
    }
    @Override
    @Transactional
    public void enableOrDisable(int id) throws EntityNotFoundException{
        Medicine medicine = medicineRepository.findById(id);
        if(medicine != null){
            medicine.setActive((medicine.isActive())? false : true);
            medicineRepository.save(medicine);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public void update(Medicine medicine) throws EntityCreatingException{
        try{
            medicineRepository.save(medicine);
        }catch (Exception ec){
            throw new EntityCreatingException("Error in updating medicine");
        }
    }

    @Override
    public Medicine getMedicine(int id) throws MedicineNotFoundException {
        Medicine medicine = medicineRepository.findById(id);
        if(medicine == null){
            throw new MedicineNotFoundException();
        }
        else{
            return medicine;
        }
    }

    @Override
    public List<Medicine> getMedicines() {
        return (List<Medicine>) medicineRepository.findAll();
    }


}
