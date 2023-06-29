package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import com.example.demo.exception.MedicineInActiveException;
import com.example.demo.exception.MedicineNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Log;
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
    public User findUser(int id) throws UserNotFoundException {
        User user = userRepo.findById(id);
        if(user != null)  return user;
        else throw new UserNotFoundException();
    }

    @Override
    public User findUser(String email) throws UserNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user != null)  return user;
        else throw new UserNotFoundException();
    }

    @Override
    public boolean changePassword(int id, String old_password, String new_password) throws UserNotFoundException, VerifyError {
        User user = userRepo.findById(id);
        if(user == null) throw new UserNotFoundException();
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
    public void addToCart(String userEmail,int medicineId) throws MedicineNotFoundException, MedicineInActiveException, UserNotFoundException {
        try{
            Log.DEBUG("Medicine ID : "+medicineId);
            Log.DEBUG("User Email : "+ userEmail);
            Medicine dbMedicine = medicineService.getMedicine(medicineId);
            if(dbMedicine.isActive()){
                User user = this.findUser(userEmail);
                if(userRepo.existsByEmailAndCart_Medicines_Id(userEmail,medicineId)){
                    Cart userCart = userRepo.findCartByEmailAndMedicineId(userEmail,medicineId);
                    userCart.setQuantity(userCart.getQuantity() +1);
                }
                else {
                    Cart newCart = new Cart(dbMedicine , 1);
                    Log.INFO(newCart.toString());
                    user.getCart().add(newCart);
                }
                userRepo.save(user);
            }
            else {
                throw new MedicineInActiveException("Medicine is Inactive");
            }
        }catch (MedicineNotFoundException ene){
            throw new MedicineNotFoundException();
        }catch (UserNotFoundException ene){
            throw new UserNotFoundException();
        }
    }

    @Override
    public void removeFromCart(String userEmail, int medicineId) throws UserNotFoundException, MedicineNotFoundException {
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

        }catch (UserNotFoundException ene){
            throw new UserNotFoundException();
        } catch (MedicineNotFoundException e) {
            throw new MedicineNotFoundException();
        }
    }

    @Override
    public void purchaseMedicines(String email, int medicineId,int quantity,double totalAmount) throws MedicineNotFoundException, MedicineInActiveException {

        try{
            Medicine medicine = medicineService.getMedicine(medicineId);
            if(medicine.isActive()){
                Purchase purchase = new Purchase(medicine,quantity,totalAmount);

            }
        }catch (EntityNotFoundException ene) {
            throw new MedicineNotFoundException();
        }

    }

    @Override
    public void purchaseMedicines(String email, List<Integer> medicineIds) throws MedicineNotFoundException, MedicineInActiveException {

    }
}
