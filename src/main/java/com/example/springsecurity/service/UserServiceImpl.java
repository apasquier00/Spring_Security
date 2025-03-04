package com.example.springsecurity.service;

import com.example.springsecurity.repository.UserDao;
import com.example.springsecurity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Qualifier("jpaUserDao")
    @Autowired
    UserDao userDao;

    public UserServiceImpl() {

    }

    public UserDto addUser(UserDto userDto) {
        userDao.add(userDto);
        return userDto;
    }

    public UserDto getByUsername(String username) {


        return userDao.getByUsername(username);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.delete(userId);

    }

    @Override
    public Stream<UserDto> getAllUsers() {
        return userDao.findAll();
    }


}
