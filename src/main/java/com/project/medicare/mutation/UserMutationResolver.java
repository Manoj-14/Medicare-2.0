package com.project.medicare.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.project.medicare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMutationResolver implements GraphQLMutationResolver {

    @Autowired
    UserRepository userRepository;

    public UserMutationResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
