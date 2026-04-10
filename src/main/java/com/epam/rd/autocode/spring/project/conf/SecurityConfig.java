/**
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.epam.rd.autocode.spring.project.security.AppUserDetailsService;
import com.epam.rd.autocode.spring.project.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
/**
 * Клас SecurityConfig відповідає за налаштування безпеки (Spring Security) у додатку.
 */
public class SecurityConfig {

    // Сервіс для завантаження користувачів із бази даних
    private final AppUserDetailsService appUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AppUserDetailsService appUserDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.appUserDetailsService = appUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    /**
     * Налаштовує основний ланцюг безпеки (SecurityFilterChain).
     * Визначає правила доступу до різних URL.
     *
     * @param http об'єкт конфігурації HTTP безпеки
     * @return налаштований SecurityFilterChain
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Вимикаємо CSRF для певних шляхів (наприклад, H2-консоль та API)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"))

            // Налаштування доступу до різних endpoint'ів
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**").permitAll() // доступ без авторизації
                    .requestMatchers("/", "/index.html", "/assets/**").permitAll() // публічні ресурси
                    .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/signup").permitAll()

                    // Доступ до книг
                    .requestMatchers("/api/books/**").hasAnyRole("EMPLOYEE", "CUSTOMER", "ADMIN")

                    // Доступ до клієнтів
                    .requestMatchers("/api/clients/me").hasAnyRole("CUSTOMER", "ADMIN")
                    .requestMatchers("/api/clients/**").hasAnyRole("EMPLOYEE", "ADMIN")

                    // Доступ до співробітників тільки для ADMIN
                    .requestMatchers("/api/employees/**").hasRole("ADMIN")

                    // Замовлення доступні для будь-якого авторизованого користувача
                    .requestMatchers("/api/orders/**").authenticated()

                    // Усі інші запити потребують авторизації
                    .anyRequest().authenticated()
            )

            // Підтримуємо дві стратегії аутентифікації: БД + in-memory
            .authenticationProvider(databaseAuthenticationProvider())
            .authenticationProvider(inMemoryAuthenticationProvider())

            // JWT + stateless security
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // Використовуємо базову HTTP-аутентифікацію (Basic Auth)
            .httpBasic(Customizer.withDefaults())

            // Дозволяємо відображення H2-консолі у фреймі
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    /**
     * Налаштовує провайдер аутентифікації.
     * Використовує DAO (робота з базою даних).
     *
     * @return AuthenticationProvider
     */
    public AuthenticationProvider databaseAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // Сервіс, який завантажує користувача
        provider.setUserDetailsService(appUserDetailsService);

        // Енкодер паролів
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationProvider inMemoryAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(inMemoryUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    /**
     * Створює користувача в пам'яті (in-memory).
     * Використовується для тестування або адмін-доступу.
     *
     * @return UserDetailsService
     */
    public UserDetailsService inMemoryUserDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin@bookstore.local")
                        .password(passwordEncoder().encode("Admin!234"))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    /**
     * Енкодер паролів.
     * NoOp означає, що паролі НЕ шифруються (тільки для тестів!).
     *
     * @return PasswordEncoder
     */
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}