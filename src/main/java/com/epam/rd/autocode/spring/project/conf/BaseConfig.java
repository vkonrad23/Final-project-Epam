/**
 * Цей файл визначає основні Java-типи та поведінку для цього модуля/функціоналу.
 */
package com.epam.rd.autocode.spring.project.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
/**
 * Клас BaseConfig відповідає за базову конфігурацію Spring додатку.
 * Тут налаштовуються біни (Beans), локалізація та перехоплювачі (interceptors).
 */
public class BaseConfig implements WebMvcConfigurer {

    @Bean
    /**
     * Створює та повертає об'єкт ModelMapper.
     * Використовується для автоматичного мапінгу між DTO та Entity.
     *
     * @return екземпляр ModelMapper
     */
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    /**
     * Налаштовує джерело повідомлень (messages) для локалізації.
     * Використовує файли messages.properties (та їх локалізовані версії).
     *
     * @return налаштований MessageSource
     */
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        
        // Вказує, де знаходяться файли локалізації (наприклад: messages_en.properties)
        messageSource.setBasename("classpath:messages");
        
        // Встановлює кодування
        messageSource.setDefaultEncoding("UTF-8");
        
        return messageSource;
    }

    @Bean
    /**
     * Визначає, як буде зберігатися обрана мова користувача.
     * У цьому випадку — в cookie.
     *
     * @return LocaleResolver
     */
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver("locale");
        
        // Мова за замовчуванням
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        
        return localeResolver;
    }

    @Bean
    /**
     * Перехоплювач, який дозволяє змінювати мову через параметр у URL.
     * Наприклад: ?lang=uk
     *
     * @return LocaleChangeInterceptor
     */
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        
        // Назва параметра, через який змінюється мова
        interceptor.setParamName("lang");
        
        return interceptor;
    }

    @Override
    /**
     * Додає перехоплювачі до Spring MVC.
     * Тут ми додаємо localeChangeInterceptor.
     *
     * @param registry реєстр перехоплювачів
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}