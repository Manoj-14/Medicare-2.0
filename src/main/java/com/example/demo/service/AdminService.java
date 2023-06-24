package com.example.demo.service;

import com.example.demo.entity.Admin;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public interface AdminService {
    @Transactional
    int create(Admin admin);

    Admin find(String email);

    @Transactional
    boolean changePassword(int id, String old_password, String new_password) throws EntityNotFoundException, VerifyError;
}
