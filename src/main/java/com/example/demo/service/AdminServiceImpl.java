package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.utils.Log;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public int create(Admin admin){
        if(!adminRepository.existsAdminByEmail(admin.getEmail())) {
            adminRepository.save(admin);
            return admin.getId();
        }else{
            throw new EntityExistsException();
        }
    }

    @Override
    public Admin find(String email) throws EntityNotFoundException{
        Admin admin = adminRepository.findAdminByEmail(email);
        if(admin.equals(null)){
            throw new EntityNotFoundException();
        }else{
            return admin;
        }
    }

    @Override
    @Transactional
    public boolean changePassword(int id, String old_password, String new_password) throws EntityNotFoundException, VerifyError {
        Admin admin= adminRepository.findById(id);
        if(admin == null) throw new EntityNotFoundException();
        else{
            if (!Objects.equals(admin.getPassword(), old_password)){
                throw new VerifyError();
            }
            else{
                admin.setPassword(new_password);
                adminRepository.save(admin);
                return true;
            }
        }
    }

    @Override
    public Admin authenticate(String email, String password) throws EntityNotFoundException {
        Log.DEBUG(email+":"+password);
        Admin admin = adminRepository.findAdminByEmailAndPassword(email,password);
        if(admin != null){
            return admin;
        }
        else {
            throw new EntityNotFoundException();
        }
    }
}
