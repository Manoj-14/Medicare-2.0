package com.project.medicare.service;

import com.project.medicare.entity.Admin;
import com.project.medicare.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public interface AdminService {
    @Transactional
    int create(Admin admin);
    Admin find(String email) throws EntityNotFoundException;
    Admin find(int id) throws UserNotFoundException;
    @Transactional
    boolean changePassword(int id, String old_password, String new_password) throws EntityNotFoundException, VerifyError;
    Admin authenticate(String email,String password) throws EntityNotFoundException;

}
