package com.project.medicare.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.project.medicare.entity.User;
import com.project.medicare.repository.UserRepository;
import org.springframework.stereotype.Component;

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
