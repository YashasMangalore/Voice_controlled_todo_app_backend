package com.todo.voice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Configuration
public class JwtConfig {
    public static final String PUBLIC_KEY ="------BEGIN PUBLIC KEY------qwjcfhgwiuf9eufuiofghjo------END PUBLIC KEY------";
    @Bean
    public JwtParser jwtParser(){
        return Jwts.parser().setSigningKey(getRSAPublicKey()).build();
    }

    private RSAPublicKey getRSAPublicKey(){
        try {
            String publicKeyPEM= PUBLIC_KEY.replace("------BEGIN PUBLIC KEY------","")
                            .replace("------END PUBLIC KEY------","")
                            .replaceAll("\\s+", "");//"Remove all groups of one or more whitespace characters from the string.
            byte[] publicKeyBytes=Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec spec=new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory kf=KeyFactory.getInstance("RSA");
            return (RSAPublicKey) kf.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA Exception "+e);
        }
    }

    public Claims parseToken(String token){
        try {
            return jwtParser().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserRoles(String token){
        Claims claims=parseToken(token);
        return claims.get("role",String.class);
    }

    public String getUserEmail(String token){
        Claims claims=parseToken(token);
        return claims.get("email",String.class);
    }
    public String getUserPassword(String token){
        Claims claims=parseToken(token);
        return claims.get("password",String.class);
    }
    public boolean getUserExpired(String token){
        Claims claims=parseToken(token);
        return claims.getExpiration().before(new Date());
    }



}
