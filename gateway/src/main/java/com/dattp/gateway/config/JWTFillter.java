package com.dattp.gateway.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class JWTFillter implements WebFilter{
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // System.out.println("================   FILLTER   ===================");
        // if(exchange.getRequest().getHeaders().get("access_token")==null){
        //     return chain.filter(exchange);
        // }
        // String accessToken = exchange.getRequest().getHeaders().get("access_token").get(0);
        // System.out.println(accessToken);
        // if(accessToken!=null){
        //     try {
        //         // tao giai thuat giai ma
        //         Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        //         JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        //         // giai ma
        //         DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
        //         String username = decodedJWT.getSubject();
        //         String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        //         // chuyen ve dang chuan de xu ly
        //         Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //         Arrays.stream(roles).forEach(role->{
        //             authorities.add(new SimpleGrantedAuthority(role));
        //         });
        //         // neu nguoi dung hop le thi set thong tin cho security context
        //         UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
        //         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //         return chain.filter(exchange);
        //     } catch (Exception e) {
        //         exchange.getResponse().getHeaders().add("error", e.getMessage());
        //         exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        //         Map<String,Object> error = new HashMap<>();
        //         error.put("code", HttpStatus.FORBIDDEN.value());
        //         error.put("message", e.getMessage());
        //         exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //         DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(error.toString().getBytes());
        //         return exchange.getResponse().writeWith(Flux.just(buffer));
        //     }
        // }else{
            return chain.filter(exchange);
        // }
    }
    
}
