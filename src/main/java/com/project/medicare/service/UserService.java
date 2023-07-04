package com.project.medicare.service;

import com.project.medicare.entity.User;
import com.project.medicare.exception.MedicineInActiveException;
import com.project.medicare.exception.MedicineNotFoundException;
import com.project.medicare.exception.UserNotFoundException;
import com.project.medicare.mapper.PurchaseMapper;
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
    void purchaseMedicines(String email, int medicineId, int quantity,double totalAmount) throws  MedicineInActiveException,UserNotFoundException;
    @Transactional
    void purchaseMedicines(String email,List<PurchaseMapper> purchases) throws MedicineInActiveException, UserNotFoundException, MedicineNotFoundException;

}
