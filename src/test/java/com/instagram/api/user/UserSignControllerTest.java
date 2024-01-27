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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//    @Test
//    @DisplayName("회원 가입 성공")
//    void registerSuccess() throws Exception {
//        UserRegistRequest request = userRequest();
//        UserResponse response = userResponse();
//
//        doReturn(response).when(userSignService)
//                .register(any(UserRegistRequest.class));
//
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/register")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(request))
//        );
//
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("name", response.getName()).exists())
//                .andExpect(jsonPath("uid", response.getUid()).exists())
//                .andExpect(jsonPath("pw", response.getPw()).exists())
//                .andExpect(jsonPath("role", response.getRole()).exists());
//    }

//    @Test
//    @DisplayName("로그인 성공")
//    void loginSuccess() throws Exception {
//        setup();
//
//        UserLoginRequest request = userLoginRequest();
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(request)))
//                .andExpect(status().isOk());
//
//    }

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

    private UserResponse userResponse() {
        return UserResponse.builder()
                .uid("test")
                .pw("test2")
                .name("COW")
                .age(25)
                .phoneNumber("000-0000-0000")
                .role(Role.USER)
                .build();
    }

}
