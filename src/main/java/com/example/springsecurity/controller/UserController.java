package com.example.springsecurity.controller;

import com.example.springsecurity.dto.NewUserDto;
import com.example.springsecurity.dto.UserDto;
import com.example.springsecurity.dto.UserRole;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin")
    public String newAdmin (@RequestBody NewUserDto newUserDto){
        UserDto userAdmin = new UserDto(newUserDto.username(),
                passwordEncoder.encode(newUserDto.password()),
                UserRole.ADMIN);
        userService.addUser(userAdmin);
        return "Administrateur " + userAdmin.username() + " bien créé";
    }

    @GetMapping("/list")
    public String listUsers (){
        Stream<UserDto> users = userService.getAllUsers();
        StringBuilder userList = new StringBuilder();
        users.forEach(userDto -> {
            userList.append(userDto.username()).append(", ");});
        return userList.toString();
    }

    @DeleteMapping("/user")
    public String deleteUser (@RequestBody UserDto userDto){
        userService.deleteUser(userDto.username());
        return "Utilisateur " + userDto.username() + " supprimé";
    }
}
