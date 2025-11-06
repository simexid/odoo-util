package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleOrderLineHookFullTest {

    @Test
    void exerciseManyGettersSettersAndToString() {
        SaleOrderLineHook h = new SaleOrderLineHook();
        h.setId(123);
        h.setAction("write");
        h.setName("Line name");
        h.setProductQty(5.5);
        h.setProductId(42);
        h.setSequence(7);
        h.setState("draft");
        h.setPriceUnit(12.3);
        h.setPriceTotal(61.5);
        h.setInvoiceStatus("invoiced");
        h.setInvoiceLines(List.of(1,2,3));

        assertEquals(123, h.getId());
        assertEquals("write", h.getAction());
        assertEquals("Line name", h.getName());
        assertEquals(5.5, h.getProductQty());
        assertEquals(42, h.getProductId());
        assertEquals(7, h.getSequence());
        assertEquals("draft", h.getState());
        assertEquals(12.3, h.getPriceUnit());
        assertEquals(61.5, h.getPriceTotal());
        assertEquals("invoiced", h.getInvoiceStatus());
        assertNotNull(h.getInvoiceLines());
        assertEquals(3, h.getInvoiceLines().size());

        String json = h.toString();
        assertTrue(json.contains("Line name"));
        assertTrue(json.contains("123"));
    }
}
