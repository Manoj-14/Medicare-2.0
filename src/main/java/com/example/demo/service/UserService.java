package com.example.demo.service;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.User;
import com.example.demo.exception.MedicineInActiveException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


import java.util.List;

public interface UserService {
    List<User> findAll();
    @Transactional
    int create(User user);
    User findUser(int id) throws EntityNotFoundException;
    User findUser(String email) throws EntityNotFoundException ;
    @Transactional
    boolean changePassword(int id, String old_password,String new_password) throws EntityNotFoundException , VerifyError;
    @Transactional
    void addToCart(String userEmail,int medicineId) throws EntityNotFoundException, MedicineInActiveException;
    @Transactional
    void removeFromCart(String userEmail,int medicineId) throws EntityNotFoundException;


}
