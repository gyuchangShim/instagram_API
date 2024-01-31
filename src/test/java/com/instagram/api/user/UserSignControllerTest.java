package com.instagram.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.instagram.api.user.controller.UserSignController;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.dto.request.UserLoginRequest;
import com.instagram.api.user.dto.request.UserRegistRequest;
import com.instagram.api.user.dto.response.UserLoginResponse;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.user.service.UserSignService;
import com.instagram.api.user.state.Role;
import com.instagram.api.util.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableWebMvc
@SpringBootTest
public class UserSignControllerTest {

    @Mock
    private UserSignService userSignService;

    @InjectMocks
    private UserSignController userSignController;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userSignController).build();
    }

    @Test
    @DisplayName("회원 가입 성공")
    void registerSuccess() throws Exception {
        UserRegistRequest request = userRequest();

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        setup();

        UserLoginRequest request = userLoginRequest();
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    private void setup() {
        User user = User.builder()
                .uid("test")
                .pw("test2")
                .name("COW")
                .age(25)
                .phoneNumber("000-0000-0000")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    private UserRegistRequest userRequest() {
        return UserRegistRequest.builder()
                .uid("test")
                .pw("test2")
                .name("COW")
                .age(25)
                .phoneNumber("000-0000-0000")
                .build();
    }

    private UserLoginRequest userLoginRequest() {
        return UserLoginRequest.builder()
                .uid("test")
                .pw("test2")
                .build();
    }

}
