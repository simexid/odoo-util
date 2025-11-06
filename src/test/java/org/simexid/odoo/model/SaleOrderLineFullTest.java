package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleOrderLineFullTest {

    @Test
    void exerciseSaleOrderLineAndInnerProduct() {
        SaleOrderLine line = new SaleOrderLine();
        line.setId(11);
        line.setName("LineX");
        line.setPriceUnit(9.99);
        line.setPriceSubtotal(19.98);
        line.setProductType("consu");
        line.setProductTemplateId(100);
        line.setProductUomQty(3);
        line.setPriceTotal(29.97);

        SaleOrderLine.Product p = new SaleOrderLine.Product(5, "ProdX");
        line.setProductId(p);

        assertEquals(11, line.getId());
        assertEquals("LineX", line.getName());
        assertEquals(9.99, line.getPriceUnit());
        assertEquals(19.98, line.getPriceSubtotal());
        assertEquals("consu", line.getProductType());
        assertEquals(100, line.getProductTemplateId());
        assertEquals(3, line.getProductUomQty());
        assertEquals(29.97, line.getPriceTotal());
        assertNotNull(line.getProductId());
        assertEquals(5, line.getProductId().getId());
        assertEquals("ProdX", line.getProductId().getName());

        String json = line.toString();
        assertTrue(json.contains("LineX"));
        assertTrue(json.contains("ProdX"));
    }
}
