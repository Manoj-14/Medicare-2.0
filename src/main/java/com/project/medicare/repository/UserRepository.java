package com.project.medicare.repository;

import com.project.medicare.entity.Cart;
import com.project.medicare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    User findById(int id);
    User findByEmail(String email);
    User findUserByEmailAndPassword(String email,String password);
    boolean existsByEmailAndCart_Medicines_Id(String email , int medicineId);
    @Query("SELECT cart FROM User user JOIN user.cart cart JOIN cart.medicines medicine WHERE user.email = :userEmail AND medicine.id = :medicineId")
    Cart findCartByEmailAndMedicineId(String userEmail, int medicineId);

}
