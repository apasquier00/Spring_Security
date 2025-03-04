package com.example.springsecurity.dto;

import java.util.UUID;

public record UserDto(String username, String password, UserRole role) {

}
