package com.project.medicare.service;

import com.project.medicare.entity.User;
import com.project.medicare.exception.MedicineInActiveException;
import com.project.medicare.exception.MedicineNotFoundException;
import com.project.medicare.exception.UserNotFoundException;
import com.project.medicare.mapper.PurchaseMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();
    @Transactional
    int create(User user);
    User findUser(int id) throws UserNotFoundException;
    User findUser(String email) throws UsernameNotFoundException;

    User authenticate(String email, String password) throws UserNotFoundException;
    @Transactional
    void changePassword(int id, String old_password,String new_password) throws UserNotFoundException , VerifyError;
    @Transactional
    void addToCart(String userEmail,int medicineId) throws MedicineNotFoundException, MedicineInActiveException, UserNotFoundException;
    @Transactional
    void removeFromCart(String userEmail,int medicineId) throws UserNotFoundException, MedicineNotFoundException;
    @Transactional
    void purchaseMedicines(String email, int medicineId, int quantity,double totalAmount) throws  MedicineInActiveException,UserNotFoundException;
    @Transactional
    void purchaseMedicines(String email,List<PurchaseMapper> purchases) throws MedicineInActiveException, UserNotFoundException, MedicineNotFoundException;

}
