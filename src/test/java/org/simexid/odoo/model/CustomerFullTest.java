package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFullTest {

    @Test
    void exerciseCustomerGettersSetters() {
        Customer c = new Customer();
        c.setId(88);
        c.setName("ACME");
        c.setEmail("contact@acme.test");
        c.setPhone("+123");
        c.setVat("VAT123");
        c.setCompanyName("ACME Srl");
        c.setCountryCode("IT");

        assertEquals(88, c.getId());
        assertEquals("ACME", c.getName());
        assertEquals("contact@acme.test", c.getEmail());
        assertEquals("+123", c.getPhone());
        assertEquals("VAT123", c.getVat());
        assertEquals("ACME Srl", c.getCompanyName());
        assertEquals("IT", c.getCountryCode());
    }
}
