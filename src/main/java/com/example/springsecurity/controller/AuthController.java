package com.example.springsecurity.controller;

import com.example.springsecurity.dto.*;
import com.example.springsecurity.service.UserService;
import com.example.springsecurity.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil = new JwtUtil();
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @PostMapping("/signup")
    public String signup(@RequestBody @Valid NewUserDto userDto) {
        if (userDto.password().length() < 4 || userDto.password().length() > 20) {
            throw new IllegalArgumentException("Le mot de passe doit comporter entre 4 et 20 caractères");
        }
        UserDto encoded = new UserDto(userDto.username(),
                passwordEncoder.encode(userDto.password()), UserRole.USER);
        userService.addUser(encoded);
        return "Utilisateur " + userDto.username() + " bien créé";
    }



    @PostMapping("/login")
    public TokenDto login(@RequestBody UserCredentialsDto userCredentialsDto) {
        System.out.println(jwtUtil.generateToken("admin"));
        final Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userCredentialsDto.username(), userCredentialsDto.password()
                        )
                );
        String token = jwtUtil.generateToken(userCredentialsDto.username());
        return new TokenDto(token, userCredentialsDto.username());
    }
}
