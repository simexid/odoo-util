package org.simexid.odoo.service;

import org.junit.jupiter.api.Test;
import org.simexid.odoo.service.impl.CrmServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class CrmServiceImplTest {

    @Test
    void isNumeric_withInteger_returnsSame() {
        Object result = CrmServiceImpl.isNumeric(5);
        assertTrue(result instanceof Integer);
        assertEquals(5, result);
    }

    @Test
    void isNumeric_withNumericString_returnsInteger() {
        Object result = CrmServiceImpl.isNumeric("42");
        assertTrue(result instanceof Integer);
        assertEquals(42, result);
    }

    @Test
    void isNumeric_withNonNumericString_returnsString() {
        Object result = CrmServiceImpl.isNumeric("abc");
        assertTrue(result instanceof String);
        assertEquals("abc", result);
    }

    @Test
    void isNumeric_withNull_throws() {
        assertThrows(IllegalArgumentException.class, () -> CrmServiceImpl.isNumeric(null));
    }
}
