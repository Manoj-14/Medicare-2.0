package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.utils.Log;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        }catch (EntityNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User not found");
        }

    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable int id,@RequestParam String old_password, @RequestParam String new_password){
        try{
            Log.INFO("Old Password:"+old_password);
            Log.INFO("New Password:"+new_password);
            userService.changePassword(id,old_password,new_password);
            return new ResponseEntity<>("Password changed", HttpStatus.OK);
        }catch (EntityNotFoundException ene){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }catch (VerifyError ve){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Password not matched");
        }
    }
}
