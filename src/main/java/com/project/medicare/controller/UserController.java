package com.project.medicare.controller;

import com.project.medicare.authentication.AuthenticationRequest;
import com.project.medicare.authentication.AuthenticationResponse;
import com.project.medicare.dto.UserDto;
import com.project.medicare.entity.User;
import com.project.medicare.exception.MedicineInActiveException;
import com.project.medicare.exception.MedicineNotFoundException;
import com.project.medicare.exception.UserNotFoundException;
import com.project.medicare.jwt.service.ApplicationUserDetailsService;
import com.project.medicare.jwt.utils.JwtUtil;
import com.project.medicare.mapper.PurchaseMedicinesRequestMapper;
import com.project.medicare.service.UserService;
import com.project.medicare.utils.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto user ){
        try{
            UserDto userResp = userService.create(user);
            return new ResponseEntity<>(userResp,HttpStatus.OK);
        }catch (DuplicateKeyException dke){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User already exists");
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error Found");
        }
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
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse authenticate(@NotNull @RequestBody AuthenticationRequest request){
        User user;
        try{
            user = userDetailsService.authenticate(request.getEmail(),request.getPassword());
        }catch (NoSuchAlgorithmException une){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        System.out.println(userDetails);
        var jwt = jwtTokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id,@NotNull @RequestBody Map<String,String> request){
        String old_password = request.get("oldPassword");
        String new_password = request.get("newPassword");
        try{
            userService.changePassword(id,old_password,new_password);
            return new ResponseEntity<>(HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (MedicineNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Medicine not found");
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
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
            return new ResponseEntity<>(HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User Not found");
        }catch (MedicineInActiveException mae){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Medicine is inactive");
        } catch (MedicineNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Medicine not found");
        }
    }

}
