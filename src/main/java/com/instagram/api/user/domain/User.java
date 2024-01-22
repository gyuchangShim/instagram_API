package com.instagram.api.user.domain;

import com.fasterxml.uuid.Generators;
import com.instagram.api.user.state.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Column(nullable = false, scale = 20, unique = true)
    private String uid;
    private String pw;
    private String name;
    private int age;
    private String phoneNumber;
//    private String fileName;
//    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    @Builder
//    public User(String uid, String pw, String name, int age, String phoneNumber,
//                String originalFileName, String imageUrl, Role role) {
//        this.uid = uid;
//        this.pw = pw;
//        this.name = name;
//        this.age = age;
//        this.phoneNumber = phoneNumber;
//        this.fileName = originalFileName;
//        this.imageUrl = imageUrl;
//        this.role = role;
//    }

    @Builder
    public User(String uid, String pw, String name, int age, String phoneNumber, Role role) {
        this.uid = uid;
        this.pw = pw;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void addUserAuthority() {
        this.role = Role.USER;
    }

//    public void updateImage(String imageUrl, String originalFileName) {
//        this.imageUrl = imageUrl;
//        this.fileName = originalFileName;
//    }

    public void updateUser(String name, int age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

}
