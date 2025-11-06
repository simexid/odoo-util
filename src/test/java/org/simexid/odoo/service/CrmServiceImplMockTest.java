package org.simexid.odoo.service;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.simexid.odoo.enums.CrmSearchEnum;
import org.simexid.odoo.model.Customer;
import org.simexid.odoo.model.SaleOrder;
import org.simexid.odoo.model.SaleOrderLine;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrmServiceImplMockTest {

    XmlRpcClient xmlRpcClient;
    CrmServiceImpl crmService;

    @BeforeEach
    void setup() throws Exception {
        xmlRpcClient = mock(XmlRpcClient.class);

        // default config instance
        XmlRpcClientConfigImpl cfg = new XmlRpcClientConfigImpl();
        cfg.setServerURL(new URL("http://example.com/xmlrpc/2/"));
        when(xmlRpcClient.getConfig()).thenReturn(cfg);

        // Mock execute to return different data depending on method and params
        when(xmlRpcClient.execute(any(String.class), any(Object[].class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String method = invocation.getArgument(0);
                Object[] params = invocation.getArgument(1);
                if ("authenticate".equals(method)) {
                    return 42; // uid
                }
                if ("execute_kw".equals(method)) {
                    // params: db, uid, apiKey, model, method, ...
                    String model = (String) params[3];
                    String call = (String) params[4];
                    if ("res.partner".equals(model) && "search_read".equals(call)) {
                        Map<String, Object> p = new HashMap<>();
                        p.put("id", 1);
                        p.put("name", "ACME");
                        p.put("email", "acme@example.org");
                        return List.of(p);
                    }
                    if ("sale.order".equals(model) && "search_read".equals(call)) {
                        Map<String, Object> so = new HashMap<>();
                        so.put("id", 100);
                        so.put("name", "SO100");
                        so.put("amount_total", 123.45);
                        so.put("partner_id", new Object[]{5, "ClientX"});
                        so.put("order_line", List.of(1000));
                        return List.of(so);
                    }
                    if ("sale.order.line".equals(model) && "search_read".equals(call)) {
                        Map<String, Object> line = new HashMap<>();
                        line.put("id", 1000);
                        line.put("name", "Item 1");
                        line.put("product_id", new Object[]{21, "ProdX"});
                        line.put("price_total", 50.0);
                        line.put("price_subtotal", 45.0);
                        return List.of(line);
                    }
                    if ("res.partner".equals(model) && "fields_get".equals(call)) {
                        Map<String, Object> attrs = new HashMap<>();
                        attrs.put("string", "Name");
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", attrs);
                        return map;
                    }
                    // generic fallback
                    return List.of();
                }
                return null;
            }
        });

        crmService = new CrmServiceImpl(xmlRpcClient);
        // set properties
        ReflectionTestUtils.setField(crmService, "apiUrl", "http://example.com/xmlrpc/2/");
        ReflectionTestUtils.setField(crmService, "username", "user");
        ReflectionTestUtils.setField(crmService, "apiKey", "key");
        ReflectionTestUtils.setField(crmService, "db", "db1");
    }

    @Test
    void testAuthenticateAndGetPartners() throws Exception {
        int uid = crmService.authenticate();
        assertTrue(uid > 0);

        List<Customer> partners = crmService.getAllPartners(null);
        assertNotNull(partners);
        assertEquals(1, partners.size());
        assertEquals("ACME", partners.get(0).getName());
    }

    @Test
    void testGetSalesAndOrderLines() throws Exception {
        List<SaleOrder> sales = crmService.getSales(CrmSearchEnum.SearchField.name, CrmSearchEnum.SearchOperator.equal, "SO100", null);
        assertNotNull(sales);
        assertEquals(1, sales.size());
        SaleOrder so = sales.get(0);
        assertEquals("SO100", so.getName());
        assertNotNull(so.getOrderLineObject());
        assertEquals(1, so.getOrderLineObject().size());
        SaleOrderLine line = so.getOrderLineObject().get(0);
        assertEquals("Item 1", line.getName());
        assertEquals(50.0, line.getPriceTotal());
    }

    @Test
    void testGetFieldsAndGeneric() throws Exception {
        Object fields = crmService.getFields(CrmSearchEnum.Model.partner);
        assertTrue(fields instanceof Map);

        Object generic = crmService.getGenericObject(CrmSearchEnum.Model.partner);
        assertNotNull(generic);
    }
}
