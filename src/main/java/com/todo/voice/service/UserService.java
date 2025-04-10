package com.todo.voice.service;

import com.todo.voice.model.User;

public interface UserService {
    User saveToDb(String email,String password);
//    User validateUser(String token);
    User findUserByEmail(String email);
}
