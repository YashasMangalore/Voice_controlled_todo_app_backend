package com.todo.voice.util;

import com.todo.voice.analyzers.MyAnalyzer;
import com.todo.voice.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserHelper {
    final MyAnalyzer myAnalyzer;
    public boolean isValidUser(String email, String password, User user){
        String processedEmail=myAnalyzer.stem(email);
        return processedEmail.equals(email) && user.getPassword().equals(password);
    }
}
