/** 
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
/**
 * Клас LoggingAspect відповідає за логування викликів методів та помилок у застосунку.
 */
public class LoggingAspect {

    // Логер для виводу повідомлень у консоль або файл
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Виконується ПЕРЕД викликом будь-якого методу в пакеті service.impl
     * (та його підпакетах).
     */
    @Before("execution(* com.epam.rd.autocode.spring.project.service.impl..*(..))")
    /**
     * Метод logServiceCall логуватиме кожен виклик сервісного методу.
     *
     * @param joinPoint містить інформацію про викликаний метод
     */
    public void logServiceCall(JoinPoint joinPoint) {
        log.debug(
            "Виклик сервісу: {} з {} аргументами",
            joinPoint.getSignature().toShortString(),
            joinPoint.getArgs().length
        );
    }

    /**
     * Виконується ПІСЛЯ виникнення виключення в будь-якому методі проєкту.
     */
    @AfterThrowing(
        pointcut = "execution(* com.epam.rd.autocode.spring.project.service..*(..)) || " +
                "execution(* com.epam.rd.autocode.spring.project.controller..*(..))",
        throwing = "ex"
    )
    /**
     * Метод logException логуватиме помилки, які виникають у застосунку.
     *
     * @param joinPoint містить інформацію про метод, де сталася помилка
     * @param ex саме виключення (помилка)
     */
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error(
                "Помилка в {}: {}",
            joinPoint.getSignature().toShortString(),
            ex.getMessage()
        );
    }
}