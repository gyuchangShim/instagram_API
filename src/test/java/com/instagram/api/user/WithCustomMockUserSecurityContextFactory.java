package com.instagram.api.user;

import com.instagram.api.user.domain.User;
import com.instagram.api.user.state.Role;
import com.instagram.api.util.jwt.PrincipalDetails;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        User user = User.builder()
                .uid("test")
                .pw("test2")
                .name("COW")
                .age(25)
                .phoneNumber("000-0000-0000")
                .role(Role.USER)
                .build();

        PrincipalDetails userDetails = PrincipalDetails.builder()
                .user(user)
                .build();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, "test2",
                        List.of(new SimpleGrantedAuthority(annotation.role())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
