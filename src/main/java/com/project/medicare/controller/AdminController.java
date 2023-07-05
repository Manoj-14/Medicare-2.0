package com.project.medicare.controller;

import com.project.medicare.entity.Admin;
import com.project.medicare.exception.UserNotFoundException;
import com.project.medicare.service.AdminService;
import com.project.medicare.utils.Log;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdmin(@PathVariable int id){
        try{
            Admin admin = adminService.find(id);
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }catch (UserNotFoundException enf){
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

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id,@NotNull @RequestBody Map<String,String> request){
        String old_password = request.get("oldPassword");
        String new_password = request.get("newPassword");
        try{
            adminService.changePassword(id,old_password,new_password);
            return new ResponseEntity<>("Password changed", HttpStatus.OK);
        } catch (VerifyError ve){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Password not matched");
        } catch (EntityNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Admin not found");
        }
    }

}
