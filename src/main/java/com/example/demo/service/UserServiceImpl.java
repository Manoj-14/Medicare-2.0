package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.User;
import com.example.demo.exception.MedicineInActiveException;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepo;
    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    MedicineService medicineService;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public int create(User user) {
        userRepo.save(user);
        return user.getUser_id();
    }

    @Override
    public User findUser(int id) throws EntityNotFoundException {
        User user = userRepo.findById(id);
        if(user != null)  return user;
        else throw new EntityNotFoundException();
    }

    @Override
    public User findUser(String email) throws EntityNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user != null)  return user;
        else throw new EntityNotFoundException();
    }

    @Override
    public boolean changePassword(int id, String old_password, String new_password) throws EntityNotFoundException, VerifyError {
        User user = userRepo.findById(id);
        if(user == null) throw new EntityNotFoundException();
        else{
            if (!Objects.equals(user.getPassword(), old_password)){
                throw new VerifyError();
            }
            else{
                user.setPassword(new_password);
                userRepo.save(user);
                return true;
            }
        }
    }

    @Transactional
    @Override
    public void addToCart(String userEmail,int medicineId) throws EntityNotFoundException, MedicineInActiveException {
        try{
            Medicine dbMedicine = medicineService.getMedicine(medicineId);
            if(dbMedicine.isActive()){
                User user = this.findUser(userEmail);
                if(userRepo.existsByEmailAndCart_Medicines_Id(userEmail,medicineId)){
                    Cart userCart = userRepo.findCartByEmailAndMedicineId(userEmail,medicineId);
                    userCart.setQuantity(userCart.getQuantity() +1);
                }
                else {
                    Cart newCart = new Cart(dbMedicine , 1);
                    user.getCart().add(newCart);
                }
                userRepo.save(user);
            }
            else {
                throw new MedicineInActiveException("Medicine is Inactive");
            }
        }catch (EntityNotFoundException ene){
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void removeFromCart(String userEmail, int medicineId) throws EntityNotFoundException{
        try{
            Medicine dbMedicine = medicineService.getMedicine(medicineId);

                User user = this.findUser(userEmail);
                if(userRepo.existsByEmailAndCart_Medicines_Id(userEmail,medicineId)){
                    Cart userCart = userRepo.findCartByEmailAndMedicineId(userEmail,medicineId);
                    if(userCart.getQuantity() == 1){
                        user.getCart().remove(userCart);
                    }
                    else {
                        userCart.setQuantity(userCart.getQuantity()-1);
                    }
                }
                else {
                    throw new EntityNotFoundException("Medicine not found");
                }
                userRepo.save(user);

        }catch (EntityNotFoundException ene){
            throw new EntityNotFoundException();
        }
    }
}
