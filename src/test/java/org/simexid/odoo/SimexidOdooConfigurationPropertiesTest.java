package org.simexid.odoo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimexidOdooConfigurationPropertiesTest {

    @Test
    void testGettersAndSetters() {
        SimexidOdooConfigurationProperties p = new SimexidOdooConfigurationProperties();
        p.setOdooApiUrl("u");
        p.setOdooUsername("user");
        p.setOdooApiKey("k");
        p.setOdooDb("db");
        p.setApiEnabled(true);
        p.setApiPath("path");
        p.setApiKeys(new String[]{"a"});

        assertEquals("u", p.getOdooApiUrl());
        assertEquals("user", p.getOdooUsername());
        assertEquals("k", p.getOdooApiKey());
        assertEquals("db", p.getOdooDb());
        assertTrue(p.getApiEnabled());
        assertEquals("path", p.getApiPath());
        assertArrayEquals(new String[]{"a"}, p.getApiKeys());
    }
}
