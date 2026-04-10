/**
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
/**
 * Клас HomeController відповідає за обробку запиту на головну сторінку.
 */
public class HomeController {

    @GetMapping("/")
    /**
     * Метод homePage перенаправляє користувача на index.html.
     *
     * @return шлях до головної HTML-сторінки
     */
    public String homePage() {
        // Використовується forward (внутрішнє перенаправлення на статичний ресурс)
        return "forward:/index.html";
    }
}