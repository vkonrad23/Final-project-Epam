package com.epam.rd.autocode.spring.project.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import org.springframework.stereotype.Component;

/**
 * Компонент для логування подій аутентифікації
 * Відслідковує успішні та неуспішні входи в систему
 */
@Component
public class AuthenticationEventLogger {

    // Логер для запису інформації в консоль або файл
    private static final Logger log =
            LoggerFactory.getLogger(AuthenticationEventLogger.class);

    /**
     * Метод, який викликається при успішній аутентифікації
     */
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {

        // Логуємо успішний вхід користувача
        log.info(
                "Успішна аутентифікація для користувача={}",
                event.getAuthentication().getName()
        );
    }

    /**
     * Метод, який викликається при помилці аутентифікації
     */
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {

        // Логуємо невдалу спробу входу + причину
        log.warn(
                "Помилка аутентифікації для користувача={}, причина={}",
                event.getAuthentication().getName(),
                event.getException().getMessage()
        );
    }
}