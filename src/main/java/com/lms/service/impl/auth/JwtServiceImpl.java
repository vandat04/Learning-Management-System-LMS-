package com.lms.service.impl.auth;

import org.springframework.beans.factory.annotation.Value;
import com.lms.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expiration}")
    private int TIME;

    @Override
    public String generateToken(Integer userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
    }

    @Override
    public String extractEmail(String token) { return extractAllClaims(token).getSubject(); }

    @Override
    public String extractRole(String token) { return extractAllClaims(token).get("role", String.class); }

    @Override
    public Integer extractUserId(String token) { return extractAllClaims(token).get("userId", Integer.class); }

    @Override
    public Date getExpirationTime(String token) {
        return extractAllClaims(token).getExpiration();
    }

    @Override
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
    }

}
