package com.example.demo.repository;

import com.example.demo.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin,Integer> {
    boolean existsAdminByEmail(String email);
    Admin findAdminByEmail(String email);
    Admin findById(int id);
}
