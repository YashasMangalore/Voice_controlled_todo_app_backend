package com.todo.voice.service;

import com.todo.voice.model.User;

import java.util.Optional;

public interface UserService {
    User saveToDb(String email,String password);
    //    User validateUser(String token);
    Optional<User> findUserByEmail(String email);
}
