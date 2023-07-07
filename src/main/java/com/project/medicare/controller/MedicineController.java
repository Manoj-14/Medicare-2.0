package com.project.medicare.controller;

import com.project.medicare.entity.Medicine;
import com.project.medicare.exception.EntityCreatingException;
import com.project.medicare.exception.MedicineNotFoundException;
import com.project.medicare.service.MedicineService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    MedicineService medicineService;
    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        try{
            List<Medicine> medicines = medicineService.getMedicines();
            return new ResponseEntity<>(medicines,HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable int id){
        try{
            Medicine medicine = medicineService.getMedicine(id);
            return new ResponseEntity<Medicine>(medicine,HttpStatus.OK);
        }catch (MedicineNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Medicine not found");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Medicine medicine){
        try{
            Medicine addedMedicine = medicineService.add(medicine);
            return new ResponseEntity<>(medicine,HttpStatus.CREATED);
        } catch (EntityCreatingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@NotNull @RequestBody Medicine medicine){
        try{
            medicineService.update(medicine);
            return new ResponseEntity<>(medicine.getName()+" Updated successfully",HttpStatus.OK);
        } catch (EntityCreatingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } catch (MedicineNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Medicine not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        try{
            medicineService.remove(id);
            return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
        }catch (EntityNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/isenabled")
    public ResponseEntity<?> enableOrDisabled(@RequestParam int enable){
        try{
            medicineService.enableOrDisable(enable);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }catch (EntityNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
