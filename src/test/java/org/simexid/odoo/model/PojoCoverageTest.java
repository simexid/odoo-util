package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PojoCoverageTest {

    @Test
    void testCustomerGettersSetters() {
        Customer c = new Customer();
        c.setId(7);
        c.setName("Name");
        c.setEmail("e@x.com");
        assertEquals(7, c.getId());
        assertEquals("Name", c.getName());
        assertEquals("e@x.com", c.getEmail());
    }

    @Test
    void testSaleOrderAndPartner() {
        SaleOrder so = new SaleOrder();
        SaleOrder.Partner p = new SaleOrder.Partner(2, "P");
        so.setPartnerId(p);
        so.setName("SO1");
        assertEquals("SO1", so.getName());
        assertEquals(2, so.getPartnerId().getId());
    }

    @Test
    void testSaleOrderLinePojo() {
        SaleOrderLine line = new SaleOrderLine();
        line.setName("ln");
        assertEquals("ln", line.getName());
    }
}
