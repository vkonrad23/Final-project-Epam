/** This file defines core Java types and behavior for this feature/module. */
package com.epam.rd.autocode.spring.project.exception;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents AlreadyExistExceptionTest and encapsulates related application behavior/data.
 */
public class AlreadyExistExceptionTest {
    private static Class<?> aClass;

    @BeforeAll
    static void init() throws ClassNotFoundException {
        aClass = Class.forName("com.epam.rd.autocode.spring.project.exception.AlreadyExistException");
    }

    @Test
    @DisplayName("AlreadyExistException extends RuntimeException")
    void testExtends(){
        assertEquals(RuntimeException.class, aClass.getSuperclass(),
                "AlreadyExistException should extends RuntimeException");
    }
}


