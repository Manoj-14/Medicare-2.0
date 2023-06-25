package com.example.demo.service;

import com.example.demo.entity.User;
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
}
