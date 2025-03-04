package com.example.springsecurity.repository;

import com.example.springsecurity.dto.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
public class UserEntity {

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserName(String userName) {this.userName = userName;}
    public void setRole(UserRole role) {
        this.role = role;
    }
    @Id
    private @NotNull String userName;
    private @NotNull String password;
    private @NotNull UserRole role;


    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public UserRole getRole() {
        return role;
    }


}