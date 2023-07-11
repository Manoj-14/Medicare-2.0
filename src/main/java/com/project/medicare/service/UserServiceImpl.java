package com.project.medicare.service;

import com.project.medicare.config.JwtIssuer;
import com.project.medicare.entity.Cart;
import com.project.medicare.entity.Medicine;
import com.project.medicare.entity.Purchase;
import com.project.medicare.entity.User;
import com.project.medicare.exception.MedicineInActiveException;
import com.project.medicare.exception.MedicineNotFoundException;
import com.project.medicare.exception.UserNotFoundException;
import com.project.medicare.mapper.PurchaseMapper;
import com.project.medicare.repository.MedicineRepository;
import com.project.medicare.repository.PurchaseRepository;
import com.project.medicare.repository.UserRepository;
import com.project.medicare.utils.Log;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepo;
    @Autowired
    MedicineRepository medicineRepository;
    @Autowired
    MedicineService medicineService;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    private JwtIssuer jwtIssuer;

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public int create(User user) throws DuplicateKeyException {
        if(userRepo.existsByEmail(user.getEmail())){
            throw new DuplicateKeyException("User Already exists");
        }
        else{
            userRepo.save(user);
            return user.getUser_id();
        }
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
    public Map<String, String> authenticate(String email, String password) throws UserNotFoundException {
        User user = userRepo.findUserByEmailAndPassword(email, password);
        if(user == null) throw new UserNotFoundException();
        else {
            Map<String, String> response = new HashMap<>();
            response.put("jwt",jwtIssuer.issue(user.getUser_id(),email,user));
            return response;
        }
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
                    userCart.setAmount(userCart.getQuantity() * userCart.getMedicines().getPrice());
                }
                else {
                    Cart newCart = new Cart(dbMedicine , 1,dbMedicine.getPrice() );
                    Log.INFO(this,newCart.toString());
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
                    Cart userCart = user.getCart().stream()
                            .filter(cartItem -> cartItem.getMedicines().getId() == medicineId)
                            .findFirst()
                            .orElse(null);
                    if(userCart.getQuantity() == 1){
                        user.getCart().remove(userCart);
                    }
                    else {
                        userCart.setQuantity(userCart.getQuantity()-1);
                        userCart.setAmount(userCart.getQuantity() * userCart.getMedicines().getPrice());
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

    @Transactional
    @Override
    public void purchaseMedicines(String email, int medicineId, int quantity,double totalAmount) throws  MedicineInActiveException,UserNotFoundException {
        try{
            Medicine dbMedicine = medicineService.getMedicine(medicineId);
            User user = this.findUser(email);
            Log.INFO(this,"DB Medicine:"+dbMedicine.toString());
            if(dbMedicine.isActive()){
                Purchase newPurchase = new Purchase(dbMedicine,quantity,totalAmount);
                purchaseRepository.save(newPurchase);
                Log.INFO(this,purchaseRepository.findById(newPurchase.getId()).toString());
                user.getPurchases().add(newPurchase);
            }
            else{
                throw new MedicineInActiveException("Medicine is in active");
            }
            userRepo.save(user);
        }catch (MedicineInActiveException mie){
            throw new MedicineInActiveException("Medicine is inactive");
        }catch (UserNotFoundException une){
            throw une;
        } catch (MedicineNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void purchaseMedicines(String email, List<PurchaseMapper> purchases) throws MedicineInActiveException, UserNotFoundException, MedicineNotFoundException {
        try{
            User user = this.findUser(email);
            for(PurchaseMapper purchaseMap: purchases){
                Log.INFO(this,purchaseMap.toString());
                Medicine medicine = medicineService.getMedicine(purchaseMap.getMedicineId());
                Purchase purchase = new Purchase(medicine,purchaseMap.getQuantity(),purchaseMap.getTotalAmount());
                user.getPurchases().add(purchase);
            }
            userRepo.save(user);
        }catch (MedicineInActiveException mie){
            throw new MedicineInActiveException("Medicine is inactive");
        }catch (UserNotFoundException une){
            throw une;
        } catch (MedicineNotFoundException e) {
            throw new MedicineNotFoundException();
        }
    }
}
