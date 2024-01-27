package com.instagram.api.user;

import com.instagram.api.user.controller.UserController;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.user.service.UserService;
import com.instagram.api.user.state.Role;
import com.instagram.api.util.jwt.PrincipalDetails;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    public void saveUser() {
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

//    @AfterAll
//    public void finish() {
//        userRepository.deleteAll();
//    }

    @Test
    @WithCustomMockUser
    @DisplayName("사용자 정보 조회 테스트")
    void retrieveUserTest() throws Exception{
        saveUser();
        PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findByName("COW").get());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user")
                        .with(SecurityMockMvcRequestPostProcessors.user(principalDetails)))
                .andDo(print())
                .andExpect(status().isOk());
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
