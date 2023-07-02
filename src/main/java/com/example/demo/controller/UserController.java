package com.example.demo.controller;

import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import com.example.demo.exception.MedicineInActiveException;
import com.example.demo.exception.MedicineNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.PurchaseMedicinesRequestMapper;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.utils.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user ){
        int id = userService.create(user);
        return new ResponseEntity<>("User "+id+" is created successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        System.out.println(id);
        try {
            User user = userService.findUser(id);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User not found");
        }

    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id,@RequestParam String old_password, @RequestParam String new_password){
        try{
            userService.changePassword(id,old_password,new_password);
            return new ResponseEntity<>("Password changed", HttpStatus.OK);
        }catch (UserNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }catch (VerifyError ve){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Password not matched");
        }
    }

    @PutMapping("/addToCart/{medicineId}")
    public ResponseEntity<?> addToCart(@RequestBody Map<String,String> request ,@PathVariable int medicineId){
        String email = request.get("email");
        Log.INFO(this,"Email: "+email);
        Log.INFO(this,"Medicine ID: "+medicineId);
        try{
            userService.addToCart(email,medicineId);
            return new ResponseEntity<>("Added to cart",HttpStatus.OK);
        }catch (MedicineNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Medicine not found");
        }catch (MedicineInActiveException mne){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE , "Medicine requested is inactive");
        }catch (UserNotFoundException une){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }
    @PutMapping("/removeToCart/{medicineId}")
    public ResponseEntity<?> removeFromCart(@RequestBody Map<String,String> request , @PathVariable int medicineId){
        String email = request.get("email");
        try{
            userService.removeFromCart(email,medicineId);
            return new ResponseEntity<>("Removed from cart",HttpStatus.OK);
        }catch (MedicineNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/purchase")
    public ResponseEntity<?> purchaseMedicine(@NotNull @RequestBody Map<String,Object> request){
        String email = (String) request.get("email");
        int medicineId = (int) request.get("medicineId");
        int quantity = (int) request.get("quantity");
        double totalAmount = (double) request.get("totalAmount");
        try {
            userService.purchaseMedicines(email,medicineId,quantity,totalAmount);
            return new ResponseEntity<>("Medicine purchased successfully",HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User Not found");
        }catch (MedicineInActiveException mae){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Medicine is inactive");
        }
    }

    @PutMapping("/purchases")
    public ResponseEntity<?> purchaseMedicines(@NotNull @RequestBody PurchaseMedicinesRequestMapper request){
        try {
            Log.INFO(this,"Purchases :"+request.toString());
            userService.purchaseMedicines(request.getEmail(),request.getPurchases());
            return new ResponseEntity<>("Medicines purchased successfully",HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User Not found");
        }catch (MedicineInActiveException mae){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Medicine is inactive");
        } catch (MedicineNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Medicine not found");
        }
    }

}
