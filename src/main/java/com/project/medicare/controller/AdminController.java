package com.project.medicare.controller;

import com.project.medicare.entity.Admin;
import com.project.medicare.service.AdminService;
import com.project.medicare.utils.Log;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Admin admin){
        try{
            int id = adminService.create(admin);
            return new ResponseEntity<>("Admin "+admin.getName()+" created successfully",HttpStatus.CREATED);
        }catch(EntityExistsException ee){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Admin already exists");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getAdmin(@PathVariable String email){
        try{
            Admin admin = adminService.find(email);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }catch (EntityNotFoundException enf){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    @PostMapping("/authenticate/{email}")
    public ResponseEntity<?> authenticate(@PathVariable String email, @RequestBody Map<String,String> request){
        String password = request.get("password");
        try{
            Log.DEBUG(email+":"+password);
            Admin admin = adminService.authenticate(email,password);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }catch (EntityNotFoundException enf){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Admin credentials not found");
        }
    }


}
