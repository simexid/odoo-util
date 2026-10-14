package org.simexid.odoo.service;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The generic, model-agnostic read: what it sends to Odoo and what it hands back. */
class CrmServiceImplSearchReadTest {

    private XmlRpcClient xmlRpcClient;
    private CrmServiceImpl crmService;

    @BeforeEach
    void setUp() throws Exception {
        xmlRpcClient = mock(XmlRpcClient.class);
        XmlRpcClientConfigImpl cfg = new XmlRpcClientConfigImpl();
        cfg.setServerURL(new URL("http://example.com/xmlrpc/2/"));
        when(xmlRpcClient.getConfig()).thenReturn(cfg);
        when(xmlRpcClient.execute(eq("authenticate"), any(Object[].class))).thenReturn(42);

        crmService = new CrmServiceImpl(xmlRpcClient);
        ReflectionTestUtils.setField(crmService, "apiUrl", "http://example.com/xmlrpc/2/");
        ReflectionTestUtils.setField(crmService, "username", "user");
        ReflectionTestUtils.setField(crmService, "apiKey", "key");
        ReflectionTestUtils.setField(crmService, "db", "db1");
    }

    @SuppressWarnings("unchecked")
    private Object[] sentParams() throws Exception {
        ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass(Object[].class);
        verify(xmlRpcClient).execute(eq("execute_kw"), captor.capture());
        return captor.getValue();
    }

    @Test
    void sendsModelDomainFieldsAndOptionsToSearchRead() throws Exception {
        Map<String, Object> row = new HashMap<>();
        row.put("id", 7);
        row.put("name", "SUB/0001");
        row.put("partner_id", new Object[]{3, "ACME"});
        when(xmlRpcClient.execute(eq("execute_kw"), any(Object[].class))).thenReturn(new Object[]{row});

        List<Map<String, Object>> result = crmService.searchRead(
                "subscription.contract",
                List.of(List.of("partner_id", "in", List.of(3L, 4L)), List.of("state", "=", "active")),
                List.of("name", "partner_id"), 25, 5, "next_renewal_date asc");

        Object[] params = sentParams();
        assertEquals("db1", params[0]);
        assertEquals(42, params[1]);
        assertEquals("subscription.contract", params[3]);
        assertEquals("search_read", params[4]);

        List<?> domain = (List<?>) ((Object[]) params[5])[0];
        assertEquals(List.of("partner_id", "in", List.of(3, 4)), domain.get(0)); // Long widened to int for XML-RPC
        assertEquals(List.of("state", "=", "active"), domain.get(1));

        Map<?, ?> options = (Map<?, ?>) params[6];
        assertArrayEquals(new Object[]{"name", "partner_id"}, (Object[]) options.get("fields"));
        assertEquals(25, options.get("limit"));
        assertEquals(5, options.get("offset"));
        assertEquals("next_renewal_date asc", options.get("order"));

        assertEquals(1, result.size());
        assertEquals("SUB/0001", result.get(0).get("name"));
    }

    @Test
    void optionsAreOmittedWhenNotGivenAndAnEmptyDomainMeansAllRecords() throws Exception {
        when(xmlRpcClient.execute(eq("execute_kw"), any(Object[].class))).thenReturn(new Object[]{});

        List<Map<String, Object>> result = crmService.searchRead("res.partner", null, List.of("name"), null, null, null);

        Map<?, ?> options = (Map<?, ?>) sentParams()[6];
        assertEquals(java.util.Set.of("fields"), options.keySet());
        assertTrue(result.isEmpty());
    }

    @Test
    void rejectsMissingModelOrFieldsBeforeCallingOdoo() {
        assertThrows(IllegalArgumentException.class, () -> crmService.searchRead(" ", List.of(), List.of("name"), null, null, null));
        assertThrows(IllegalArgumentException.class, () -> crmService.searchRead("res.partner", List.of(), List.of(), null, null, null));
        assertThrows(IllegalArgumentException.class, () -> crmService.searchRead("res.partner", List.of(), null, null, null, null));
    }

    @Test
    void rejectsDomainValuesXmlRpcCannotCarry() {
        List<Object> withNull = new java.util.ArrayList<>();
        withNull.add(java.util.Arrays.asList("name", "=", null));
        assertThrows(IllegalArgumentException.class, () -> crmService.searchRead("res.partner", withNull, List.of("name"), null, null, null));
        assertThrows(IllegalArgumentException.class, () -> crmService.searchRead("res.partner",
                List.of(List.of("id", "=", 99_999_999_999L)), List.of("name"), null, null, null));
    }
}
