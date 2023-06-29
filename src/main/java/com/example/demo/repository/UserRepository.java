package com.example.demo.repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findById(int id);
    User findByEmail(String email);
    boolean existsByEmailAndCart_Medicines_Id(String email , int medicineId);
    @Query("SELECT cart FROM User user JOIN user.cart cart JOIN cart.medicines medicine WHERE user.email = :userEmail AND medicine.id = :medicineId")
    Cart findCartByEmailAndMedicineId(String userEmail, int medicineId);

}
