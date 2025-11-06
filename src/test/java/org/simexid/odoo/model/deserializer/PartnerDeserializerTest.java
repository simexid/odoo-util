package org.simexid.odoo.model.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.simexid.odoo.model.SaleOrder;

import static org.junit.jupiter.api.Assertions.*;

public class PartnerDeserializerTest {

    @Test
    public void testDeserializeNumber() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SaleOrder.Partner.class, new PartnerDeserializer())
                .create();

        SaleOrder.Partner partner = gson.fromJson("123", SaleOrder.Partner.class);
        assertNotNull(partner);
        assertEquals(123, partner.getId());
        assertNull(partner.getName());
    }

    @Test
    public void testDeserializeString() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SaleOrder.Partner.class, new PartnerDeserializer())
                .create();

        SaleOrder.Partner partner = gson.fromJson("\"John Doe\"", SaleOrder.Partner.class);
        assertNotNull(partner);
        assertEquals(0, partner.getId());
        assertEquals("John Doe", partner.getName());
    }

    @Test
    public void testDeserializeArray() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SaleOrder.Partner.class, new PartnerDeserializer())
                .create();

        SaleOrder.Partner partner = gson.fromJson("[456, \"ACME Corp\"]", SaleOrder.Partner.class);
        assertNotNull(partner);
        assertEquals(456, partner.getId());
        assertEquals("ACME Corp", partner.getName());
    }
}
