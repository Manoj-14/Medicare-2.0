package com.project.medicare.service;

import com.project.medicare.dto.UserDto;
import com.project.medicare.entity.Address;
import com.project.medicare.entity.Cart;
import com.project.medicare.entity.Medicine;
import com.project.medicare.entity.User;
import com.project.medicare.exception.MedicineInActiveException;
import com.project.medicare.exception.MedicineNotFoundException;
import com.project.medicare.exception.UserNotFoundException;
import com.project.medicare.mapper.PurchaseMapper;
import jakarta.transaction.Transactional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

public interface UserService {
    List<User> findAll();
    @Transactional
    UserDto create(UserDto user) throws DuplicateKeyException , NoSuchAlgorithmException;
    User findUser(int id) throws UserNotFoundException;
    User findUser(String email) throws UsernameNotFoundException;
    User authenticate(String email, String password) throws UserNotFoundException;
    @Transactional
    void changePassword(String email, String old_password,String new_password) throws UserNotFoundException, VerifyError, NoSuchAlgorithmException;
    @Transactional
    void addToCart(String userEmail,int medicineId) throws MedicineNotFoundException, MedicineInActiveException, UserNotFoundException;
    @Transactional
    void removeFromCart(String userEmail,int medicineId) throws UserNotFoundException, MedicineNotFoundException;
    @Transactional
    void purchaseMedicines(String email, int medicineId, int quantity,double totalAmount) throws  MedicineInActiveException,UserNotFoundException;
    @Transactional
    void purchaseMedicines(String email,List<PurchaseMapper> purchases) throws MedicineInActiveException, UserNotFoundException, MedicineNotFoundException;
    List<Cart> getUserCart(String email) throws UserNotFoundException, NoSuchAlgorithmException;
    void removeMedicineFromCart(String userEmail,int medicineId) throws MedicineNotFoundException , UserNotFoundException;
    UserDto getProfile(String email) throws UserNotFoundException;
    @Transactional
    void updateAddress(String email, Address address) throws UserNotFoundException ;
    @Transactional
    void updatePhone(String email, long phone) throws UserNotFoundException ;
}
