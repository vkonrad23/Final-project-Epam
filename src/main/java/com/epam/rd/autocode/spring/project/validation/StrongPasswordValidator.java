/** Цей файл визначає основні типи та поведінку для цього модуля */
package com.epam.rd.autocode.spring.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Валідатор для анотації @StrongPassword
 * Перевіряє, чи пароль відповідає вимогам безпеки
 */
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    /**
     * Регулярний вираз для перевірки пароля:
     * ^                 → початок рядка
     * (?=.*[a-z])       → хоча б одна маленька літера
     * (?=.*[A-Z])       → хоча б одна велика літера
     * (?=.*\\d)         → хоча б одна цифра
     * (?=.*[\\W_])      → хоча б один спеціальний символ
     * .{8,}             → мінімум 8 символів
     * $                 → кінець рядка
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");

    /**
     * Основний метод валідації
     * Викликається автоматично при використанні @StrongPassword
     *
     * @param value значення (пароль), яке перевіряється
     * @param context контекст валідації (можна кастомізувати повідомлення)
     * @return true — якщо пароль валідний, false — якщо ні
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // Перевіряємо:
        // 1. пароль не null
        // 2. відповідає регулярному виразу
        return value != null && PASSWORD_PATTERN.matcher(value).matches();
    }
}