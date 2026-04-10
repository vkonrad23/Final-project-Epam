/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.conf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents SecurityConfigTest and encapsulates related application behavior/data.
 */
public class SecurityConfigTest {
    @Test
    @DisplayName("Annotation [Configuration] exist for SecurityConfig class")
    /**
     * testsConfigAnnotation: executes this operation as part of the current business flow.
     */
    public void testsConfigAnnotation() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.epam.rd.autocode.spring.project.conf.SecurityConfig");

        assertTrue(aClass.isAnnotationPresent(Configuration.class),
                String.format("Class [%s]. [@Configuration] is missed.", aClass.getSimpleName()));
    }
}


