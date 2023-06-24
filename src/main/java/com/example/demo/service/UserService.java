package com.example.demo.service;

import com.example.demo.entity.User;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;

public interface UserService {
    List<User> findAll();
    int create(User user);
    User findUser(int id) throws EntityNotFoundException;

    boolean changePassword(int id, String old_password,String new_password) throws EntityNotFoundException , VerifyError;
}
