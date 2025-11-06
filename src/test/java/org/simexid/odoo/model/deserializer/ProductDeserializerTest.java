package org.simexid.odoo.model.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.simexid.odoo.model.SaleOrderLine;

import static org.junit.jupiter.api.Assertions.*;

class ProductDeserializerTest {

    @Test
    void testDeserializeArray() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SaleOrderLine.Product.class, new ProductDeserializer())
                .create();

        SaleOrderLine.Product p = gson.fromJson("[21, \"ProdX\"]", SaleOrderLine.Product.class);
        assertNotNull(p);
        assertEquals(21, p.getId());
        assertEquals("ProdX", p.getName());
    }

    @Test
    void testDeserializePrimitive() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SaleOrderLine.Product.class, new ProductDeserializer())
                .create();

        SaleOrderLine.Product p = gson.fromJson("21", SaleOrderLine.Product.class);
        assertNotNull(p);
        assertEquals(21, p.getId());
    }
}
