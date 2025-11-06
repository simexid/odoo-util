package org.simexid.odoo.service;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simexid.odoo.model.Customer;
import org.simexid.odoo.model.SaleOrderLine;
import org.simexid.odoo.service.impl.CrmServiceImpl;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.util.ReflectionTestUtils;

class CrmServiceImplXmlRpcMockTest {

    private XmlRpcClient xmlRpcClient;
    private CrmServiceImpl svc;

    @BeforeEach
    void setup() {
        xmlRpcClient = mock(XmlRpcClient.class);
        // return a real config object for getConfig()
        when(xmlRpcClient.getConfig()).thenReturn(new XmlRpcClientConfigImpl());
        svc = new CrmServiceImpl(xmlRpcClient);
        // set necessary @Value fields used by setEndpoint()
        ReflectionTestUtils.setField(svc, "apiUrl", "http://localhost/");
        ReflectionTestUtils.setField(svc, "username", "u");
        ReflectionTestUtils.setField(svc, "apiKey", "k");
        ReflectionTestUtils.setField(svc, "db", "db");
    }

    @Test
    void testGetAllPartnersParsesResult() throws Exception {
        // authenticate returns uid
    when(xmlRpcClient.execute(eq("authenticate"), any(Object[].class))).thenReturn(10);

        // execute_kw for search_read returns a list of maps
        List<Map<String, Object>> result = List.of(Map.of("id", 1, "name", "ACME", "email", "a@x"));
    when(xmlRpcClient.execute(eq("execute_kw"), any(Object[].class))).thenReturn(result);

        List<Customer> partners = svc.getAllPartners(null);
        assertNotNull(partners);
        assertEquals(1, partners.size());
        assertEquals("ACME", partners.get(0).getName());
    }

    @Test
    void testGetOrderLineParsesProduct() throws Exception {
    when(xmlRpcClient.execute(eq("authenticate"), any(Object[].class))).thenReturn(11);

        // return a sale.order.line like structure
        List<Map<String, Object>> lines = List.of(Map.of(
                "id", 2,
                "name", "L1",
                "product_id", List.of(5, "ProdName"),
                "product_uom_qty", 3,
                "price_unit", 9.99,
                "price_total", 29.97
        ));
    when(xmlRpcClient.execute(eq("execute_kw"), any(Object[].class))).thenReturn(lines);

        List<SaleOrderLine> ol = svc.getOrderLine(List.of(2));
        assertNotNull(ol);
        assertEquals(1, ol.size());
        assertEquals("L1", ol.get(0).getName());
        assertEquals(5, ol.get(0).getProductId().getId());
    }

    @Test
    void testGetFieldsAndGenericObject() throws Exception {
    when(xmlRpcClient.execute(eq("authenticate"), any(Object[].class))).thenReturn(12);

        // First execute_kw (fields_get) returns a map of field definitions
        Map<String, Object> fieldsMap = Map.of("f1", Map.of("string", "F1"));
        // Second execute_kw (search_read) returns list of records
        List<Map<String, Object>> records = List.of(Map.of("f1", "v1"));

    when(xmlRpcClient.execute(eq("execute_kw"), any(Object[].class))).thenReturn(fieldsMap).thenReturn(records);

    // Call generic first: first execute_kw -> fieldsMap, second -> records
    Object generic = svc.getGenericObject(org.simexid.odoo.enums.CrmSearchEnum.Model.partner);
    assertTrue(generic instanceof List);

    // Reconfigure stub to return fieldsMap for a single call and verify getFields separately
    when(xmlRpcClient.execute(eq("execute_kw"), any(Object[].class))).thenReturn(fieldsMap);
    Object fields = svc.getFields(org.simexid.odoo.enums.CrmSearchEnum.Model.partner);
    assertTrue(fields instanceof Map);
    }
}
