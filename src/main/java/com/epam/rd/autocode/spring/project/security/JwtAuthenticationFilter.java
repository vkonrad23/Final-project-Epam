package com.epam.rd.autocode.spring.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фільтр для перевірки JWT токена у кожному HTTP-запиті
 * Виконується один раз для кожного запиту
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Сервіс для роботи з JWT (парсинг, валідація)
    private final JwtService jwtService;

    // Сервіс для отримання користувача з БД
    private final AppUserDetailsService userDetailsService;

    /**
     * Конструктор для ініціалізації залежностей
     */
    public JwtAuthenticationFilter(JwtService jwtService,
                                   AppUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Основний метод фільтра
     * Виконується при кожному HTTP-запиті
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        // Отримуємо заголовок Authorization
        String authHeader = request.getHeader("Authorization");

        // Якщо заголовок відсутній або не містить Bearer → пропускаємо запит
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Витягуємо JWT (після "Bearer ")
        String jwt = authHeader.substring(7);

        // Отримуємо username (email) з токена
        String username = jwtService.extractUsername(jwt);

        // Перевіряємо:
        // 1. username існує
        // 2. користувач ще не автентифікований
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            // Завантажуємо користувача з БД
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // Перевіряємо валідність токена
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Створюємо об'єкт аутентифікації
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Додаємо деталі запиту (IP, session тощо)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Встановлюємо користувача в контекст безпеки
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Передаємо запит далі по ланцюгу фільтрів
        filterChain.doFilter(request, response);
    }
}