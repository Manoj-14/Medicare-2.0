package com.example.demo.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.Query;
@Component
public class UserQueryResolver implements GraphQLQueryResolver {


    private UserRepository userRepository;

    public UserQueryResolver(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Iterable<User> findAllUser(){
        return userRepository.findAll();
    }

    public float countUser(){
        return userRepository.count();
    }
}
