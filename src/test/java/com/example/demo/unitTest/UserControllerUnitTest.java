package com.example.demo.unitTest;

import com.example.demo.controller.UserController;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void getAllUsers() throws Exception{
        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        verify(userService,times(1)).findAll();
    }
}
