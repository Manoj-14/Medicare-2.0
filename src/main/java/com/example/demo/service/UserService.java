package com.example.demo.service;

import com.example.demo.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


import java.util.List;

public interface UserService {
    List<User> findAll();
    @Transactional
    int create(User user);
    User findUser(int id) throws EntityNotFoundException;
    @Transactional
    boolean changePassword(int id, String old_password,String new_password) throws EntityNotFoundException , VerifyError;
}
