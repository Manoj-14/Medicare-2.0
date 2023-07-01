package com.example.demo.service;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import com.example.demo.exception.MedicineInActiveException;
import com.example.demo.exception.MedicineNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


import java.util.List;

public interface UserService {
    List<User> findAll();
    @Transactional
    int create(User user);
    User findUser(int id) throws UserNotFoundException;
    User findUser(String email) throws UserNotFoundException ;
    @Transactional
    boolean changePassword(int id, String old_password,String new_password) throws UserNotFoundException , VerifyError;
    @Transactional
    void addToCart(String userEmail,int medicineId) throws MedicineNotFoundException, MedicineInActiveException, UserNotFoundException;
    @Transactional
    void removeFromCart(String userEmail,int medicineId) throws UserNotFoundException, MedicineNotFoundException;
    @Transactional
    void purchaseMedicines(String email, Purchase purchase) throws  MedicineInActiveException,UserNotFoundException;
    @Transactional
    void purchaseMedicines(String email,List<Purchase> purchases) throws MedicineInActiveException, UserNotFoundException;

}
