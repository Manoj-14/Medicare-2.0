package com.project.medicare.jwt.service;

import com.project.medicare.authentication.UserPrincipal;
import com.project.medicare.repository.UserRepository;
import lombok.AllArgsConstructor;
import com.project.medicare.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class ApplicationUserDetailsServiceImpl implements ApplicationUserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        return new UserPrincipal(userRepository.findByEmail(email));
    }

    @Override
    public User authenticate(String email, String password) throws NoSuchAlgorithmException{
        if (email.isEmpty() || password.isEmpty()) throw new BadCredentialsException("Unauthorized");

        var user = userRepository.findByEmail(email);

        if(user == null) throw new BadCredentialsException("unauthorized");

        var verified = verifyPasswordHash(password,user.getPassword(), user.getStoredSalt());

        if(!verified) throw new BadCredentialsException("unauthorized");

        return user;
    }

    public Boolean verifyPasswordHash(String password, byte[] hashedPassword, byte[] storedSalt) throws NoSuchAlgorithmException {
        if (password.isBlank() || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be empty or whitespace only string.");
        if (hashedPassword.length != 64)
            throw new IllegalArgumentException("Invalid length of password hash (64 bytes expected)");
        if (storedSalt.length != 128)
            throw new IllegalArgumentException("Invalid length of password salt (64 bytes expected).");

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(storedSalt);
        var computedHash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return MessageDigest.isEqual(computedHash, hashedPassword);
    }
}
