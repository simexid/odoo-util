package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelHooksPojoTest {

    @Test
    void testSaleOrderHookPojo() {
        SaleOrderHook h = new SaleOrderHook();
        h.setId(10);
        h.setAction("create");
        h.setPartnerId(5);
        assertEquals(10, h.getId());
        assertEquals("create", h.getAction());
        assertEquals(5, h.getPartnerId());
    }

    @Test
    void testSaleOrderLineHookPojo() {
        SaleOrderLineHook h = new SaleOrderLineHook();
        h.setId(20);
        h.setName("SOLH");
        assertEquals(20, h.getId());
        assertEquals("SOLH", h.getName());
    }

    @Test
    void testCustomerHookPojo() {
        CustomerHook h = new CustomerHook();
        h.setId(30);
        h.setName("CUST");
        assertEquals(30, h.getId());
        assertEquals("CUST", h.getName());
    }
}
