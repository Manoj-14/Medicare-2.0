package com.project.medicare.utils;

import com.project.medicare.jwt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserUtility {

    @Autowired
    JwtUtil jwtTokenUtil;

    protected String getEmailFromHeader(HttpServletRequest request){
        final String authorizationHeader = request.getHeader("Authorization");
        var token = authorizationHeader.substring(7);
        return jwtTokenUtil.extractUsername(token);
    }
}
