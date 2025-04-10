package com.todo.voice.controller;

import com.todo.voice.model.User;
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
    final UserHelper userHelper;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String token){
        if(token!=null && token.startsWith("Bearer ")){
            if(jwtService.validateToken(token)){
                String email= jwtService.getEmailFromClaims(token);
                String password= jwtService.getPasswordFromClaims(token);
                User user=userService.findUserByEmail(email);
                if(user==null){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                boolean isValid=userHelper.isValidUser(email,password,user);
                if(isValid){
                    Map<String,Object> response=new HashMap<>();
                    response.put("id",user.getId());
                    response.put("email",user.getEmail());
                    return ResponseEntity.ok(response);
                }
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestHeader("Authorization") String token){
        if(token!=null && token.startsWith("Bearer ")){
            if(jwtService.validateToken(token)){
                String email= jwtService.getEmailFromClaims(token);
                String password= jwtService.getPasswordFromClaims(token);
                User user=userService.saveToDb(email,password);
                if (user==null){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","Email already exists"));
                }
                Map<String,Object> response=new HashMap<>();
                response.put("id",user.getId());
                response.put("email",user.getEmail());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
    }
}
