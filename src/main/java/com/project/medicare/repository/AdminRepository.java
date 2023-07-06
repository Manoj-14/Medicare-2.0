package com.project.medicare.repository;

import com.project.medicare.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    boolean existsAdminByEmail(String email);
    Admin findAdminByEmail(String email);
    Admin findById(int id);
    Admin findAdminByEmailAndPassword(String email,String password);
}
