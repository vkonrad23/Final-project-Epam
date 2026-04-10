package com.epam.rd.autocode.spring.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Сервіс для роботи з JWT токенами:
 * - створення токенів
 * - витягування даних з токена
 * - перевірка валідності
 */
@Service
public class JwtService {

    // Секретний ключ для підпису JWT (з application.properties)
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    // Час життя токена (у мілісекундах, за замовчуванням 1 година)
    @Value("${security.jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;

    /**
     * Отримати username (email) з токена
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Згенерувати токен без додаткових даних
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Згенерувати токен з додатковими даними (claims)
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        // Поточний момент часу
        Instant now = Instant.now();

        return Jwts.builder()
                .claims(extraClaims) // додаткові дані
                .subject(userDetails.getUsername()) // username (email)
                .issuedAt(Date.from(now)) // коли створено
                .expiration(Date.from(now.plusMillis(jwtExpirationMs))) // коли закінчується
                .signWith(getSignInKey()) // підпис токена
                .compact(); // створення JWT
    }

    /**
     * Перевірити, чи токен валідний:
     * - username співпадає
     * - токен не протермінований
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Перевірити, чи токен протермінований
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Універсальний метод для витягування будь-якого поля (claim) з токена
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Витягнути всі дані (claims) з токена
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))) // перевірка підпису
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Отримати ключ для підпису JWT
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}