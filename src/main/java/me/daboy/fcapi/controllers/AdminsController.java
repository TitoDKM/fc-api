package me.daboy.fcapi.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.daboy.fcapi.entities.Admin;
import me.daboy.fcapi.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class AdminsController {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/api/login")
    public HttpEntity<String> login(@RequestBody Map<String, Object> payload) {
        if(payload.isEmpty() || payload.get("mail") == null || payload.get("password") == null || payload.get("mail") == "" || payload.get("password") == "")
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios");
        Optional<Admin> admin = adminRepository.findAdminByEmail(payload.get("mail").toString());
        if(!admin.isPresent())
            return ResponseEntity.badRequest().body("El admin con el email introducido no existe");
        if(!bCryptPasswordEncoder.matches(payload.get("password").toString(), admin.get().getPassword()))
            return ResponseEntity.badRequest().body("Contraseña incorrecta");

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
