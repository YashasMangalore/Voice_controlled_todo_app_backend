package com.todo.voice.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {
    public static final String PRIVATE_KEY ="------BEGIN PUBLIC KEY------qwjcfhgwiuf9eufuiofghjo------END PUBLIC KEY------";
    @Bean
    public JwtParser jwtParser(){
        return Jwts.parser().setSigningKey(getRSAPrivateKey()).build();
    }

    private RSAPrivateKey getRSAPrivateKey(){
        try {
            byte[] privateKeyBytes= Base64.getDecoder().decode(
                    PRIVATE_KEY.replace("------BEGIN PUBLIC KEY------","")
                            .replace("------END PUBLIC KEY------","")
                            .replaceAll("\\s+", "")//"Remove all groups of one or more whitespace characters from the string."
            );
            PKCS8EncodedKeySpec spec=new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory kf=KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA Exception "+e);
        }
    }
}
