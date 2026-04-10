/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Кастомна анотація для перевірки складності пароля
 * Використовується разом із StrongPasswordValidator
 */
@Documented // означає, що анотація буде в документації (JavaDoc)
@Constraint(validatedBy = StrongPasswordValidator.class) // вказує, який клас виконує перевірку
@Target({ElementType.FIELD, ElementType.PARAMETER}) // можна використовувати для полів і параметрів
@Retention(RetentionPolicy.RUNTIME) // анотація доступна під час виконання програми
public @interface StrongPassword {

    /**
     * Повідомлення про помилку, якщо пароль не відповідає вимогам
     */
    String message() default
            "Пароль має містити мінімум 8 символів, великі і малі літери, цифру та спеціальний символ";

    /**
     * Групи валідації (використовується рідко, для складних сценаріїв)
     */
    Class<?>[] groups() default {};

    /**
     * Додаткові метадані (Payload) для валідації
     */
    Class<? extends Payload>[] payload() default {};
}