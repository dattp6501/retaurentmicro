package com.dattp.authservice.service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dattp.authservice.entity.User;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-accesstoken}")
    private long EXPIRATION_ACCESSTOKEN;

    @Value("${jwt.expiration-refreshtoken}")
    private long EXPIRATION_REFRESHTOKEN;

    public String generateAccessToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_ACCESSTOKEN))
            .withClaim(
                "roles", 
                authorities.stream().map(
                        GrantedAuthority::getAuthority
                    )
                    .collect(Collectors.toList()
                )
            )
            .sign(algorithm);
    }

    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_REFRESHTOKEN))
            .sign(algorithm);
    }

}
