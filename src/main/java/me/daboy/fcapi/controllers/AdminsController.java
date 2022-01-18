package me.daboy.fcapi.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AdminsController {

    @PostMapping("/api/login")
    public HttpEntity<String> login(@RequestBody Map<String, Object> payload) {
        System.out.println(payload.get("mail").toString());
        if(payload.get("mail") == null || payload.get("password") == null)
            return ResponseEntity.ok("Error");
        return ResponseEntity.ok(generateToken(payload.get("mail").toString()));
    }

    @GetMapping("/api/otra")
    public HttpEntity<String> otra() {
        return ResponseEntity.ok("Adiós");
    }

    private String generateToken(String mail) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("DEFAULT");

        String token = Jwts.builder().setId("fcTokenJWT").setSubject(mail)
                .claim("authorities", grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 172800000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
        return token;
    }
}
