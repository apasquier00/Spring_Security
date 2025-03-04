package com.example.springsecurity.repository;

import com.example.springsecurity.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class JpaUserDao implements UserDao {

    @Autowired
    UserEntityRepository userEntityRepository;

    public JpaUserDao() {
    }

    public Stream<UserDto> findAll() {
        List<UserEntity> usersEntity = userEntityRepository.findAll();
        return usersEntity.stream().map(this::convertEntityToDto);
    }

    public UserDto getByUserName(@NotNull String username){
        Optional<UserEntity> optUserEntity = userEntityRepository.findByUserName(username);
        UserEntity userEntity = optUserEntity.orElse(null);
        assert userEntity != null;
        return convertEntityToDto(userEntity);
    }


    public UserDto getByUsername(String id) {
        Optional<UserEntity> optUserEntity = userEntityRepository.findById(id);
        UserEntity userEntity = optUserEntity.orElse(null);
        assert userEntity != null;
        return convertEntityToDto(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(String id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    public UserDto add(UserDto user) {
        UserEntity userEntity = convertDtoToEntity(user);
        userEntityRepository.save(userEntity);
        return user;
    }

    public void delete(String id) {
        userEntityRepository.deleteById(id);

    }

    public UserEntity convertDtoToEntity(UserDto user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.username());
        userEntity.setPassword(user.password());
        userEntity.setRole(user.role());

        return userEntity;
    }

    public UserDto convertEntityToDto(UserEntity user) {
        return new UserDto(user.getUserName(), user.getPassword(), user.getRole());
    }

}
