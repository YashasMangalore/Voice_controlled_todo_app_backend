package com.todo.voice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    //encode private
    //decode public
    private final JwtParser jwtParser;

    public JwtService(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    public Claims parseToken(String token){
        try{
//            return Jwts.parser()  // Using Jwts.parser() for version 0.12.6
//                    .verifyWith(getSigningKey())
//                    .build()// Set the signing key for validation
//                    .parseSignedClaims(token)  // Parse and validate the JWT token
//                    .getPayload();  // Returns the claims (body of the token)
            return jwtParser.parseSignedClaims(token).getPayload();
        }
        catch (Exception e) {
            throw new RuntimeException("Invalid Jwt token "+e);
        }
    }

//    private SecretKey getSigningKey() {
//        byte[] keyBytes=SECRET_KEY.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
    public boolean validateToken(String token){
//        Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
        return true;
    }

    public String getEmailFromClaims(String token){
        Claims claims=parseToken(token);
        return claims.get("email",String.class);
    }

    public String getPasswordFromClaims(String token){
        Claims claims=parseToken(token);
        return claims.get("password",String.class);
    }
}
