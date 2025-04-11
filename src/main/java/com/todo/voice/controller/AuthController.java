package com.todo.voice.controller;

import com.todo.voice.config.JwtConfig;
import com.todo.voice.model.User;
import com.todo.voice.repository.UserRepository;
import com.todo.voice.service.JwtService;
import com.todo.voice.service.UserService;
import com.todo.voice.util.UserHelper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private JwtService jwtService;
    private UserService userService;
    private  final UserRepository userRepository;
    final UserHelper userHelper;
    private final JwtConfig jwtConfig;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String token){
        if(token!=null && token.startsWith("Bearer ")){
            token=token.substring(7);
            if(jwtConfig.getUserExpired(token)){
                String email= jwtService.getEmailFromClaims(token);
                String password= jwtService.getPasswordFromClaims(token);

                return userRepository.findByEmail(email)
                        .filter(user -> user.getPassword().equals(password))
                        .map(user -> {
                            Map<String,Object> response=new HashMap<>();
                            response.put("id",user.getId());
                            response.put("email",user.getEmail());
                            return ResponseEntity.ok(response);
                        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestHeader("Authorization") String token){
        if(token!=null && token.startsWith("Bearer ")){
            token=token.substring(7);
            if(jwtConfig.getUserExpired(token)){
                String email= jwtService.getEmailFromClaims(token);
                String password= jwtService.getPasswordFromClaims(token);

                if(userRepository.existByEmail(email)){
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(Map.of("message","Email already exist"));
                }

                User user=new User();
                user.setEmail(email);
                user.setPassword(password);
                userRepository.save(user);

                Map<String,Object> response=new HashMap<>();
                response.put("id",user.getId());
                response.put("email",user.getEmail());
                response.put("password",user.getPassword());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
    }
}
