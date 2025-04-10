package com.todo.voice.service.impl;

import com.todo.voice.analyzers.MyAnalyzer;
import com.todo.voice.model.User;
import com.todo.voice.repository.UserRepository;
import com.todo.voice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final MyAnalyzer myAnalyzer;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.existByEmail(email) ? userRepository.findByEmail(email):null;
    }

    @Override
    public User saveToDb(String email, String password) {
        String processedEmail=myAnalyzer.stem(email);

        User user=User.builder()
                .email(processedEmail)
                .password(password)
                .build();
        if(userRepository.existByEmail(email)){
            return null;
        }
        return userRepository.save(user);
    }

//    @Override
//    public User validateUser(String token) {
//        if(jwtService.validateToken(token)){
//            Long id=1L;
//            User user=authRepository.findById(id).orElse(null);
//            if(user==null){
//                log.error("Not found in DB");
//            }
//            else{
//                return user;
//            }
//        }
//        return null;
//    }

    //   @Override
//    public User addToDb(String token) {
//        if(jwtService.validateToken(token)){
//            User user=new User();
//           return authRepository.save(user);
//        }
//        return null;
//    }


}
