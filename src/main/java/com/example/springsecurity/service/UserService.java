package com.example.springsecurity.service;


import com.example.springsecurity.dto.UserDto;

import java.util.stream.Stream;

public interface UserService {
    public UserDto addUser(UserDto user);
    public UserDto getByUsername(String username);
    void deleteUser(String userId);
    Stream<UserDto> getAllUsers();
}
