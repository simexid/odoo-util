package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleOrderHookFullTest {

    @Test
    void exerciseSaleOrderHook() {
        SaleOrderHook h = new SaleOrderHook();
        h.setId(55);
        h.setAction("create");
        h.setAmountPaid(10.5);
        h.setAmountTotal(100.0);
        h.setDateOrder("2025-11-06");
        h.setOrderLine(List.of("l1", "l2"));
        h.setPartnerId(77);

        assertEquals(55, h.getId());
        assertEquals("create", h.getAction());
        assertEquals(10.5, h.getAmountPaid());
        assertEquals(100.0, h.getAmountTotal());
        assertEquals("2025-11-06", h.getDateOrder());
        assertEquals(2, h.getOrderLine().size());
        assertEquals(77, h.getPartnerId());

        String json = h.toString();
        assertTrue(json.contains("create"));
        assertTrue(json.contains("2025-11-06"));
    }
}
