package com.example.springsecurity.repository;

import com.example.springsecurity.dto.UserDto;
import jakarta.validation.constraints.NotNull;

import java.util.stream.Stream;

public interface UserDao {

    @NotNull Stream<UserDto> findAll();
    UserDto getByUsername(@NotNull String id);
    UserEntity getUserEntityById(@NotNull String id);
    UserDto getByUserName(@NotNull String username);

    UserDto add(@NotNull UserDto user);
    void delete(@NotNull String id);
}
