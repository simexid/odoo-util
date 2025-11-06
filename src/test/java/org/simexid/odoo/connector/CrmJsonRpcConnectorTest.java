package org.simexid.odoo.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrmJsonRpcConnectorTest {

    @Test
    void authenticateParsesUidAndCaches() throws Exception {
        RestTemplate rest = mock(RestTemplate.class);
        ObjectMapper mapper = new ObjectMapper();
        CrmJsonRpcConnector c = new CrmJsonRpcConnector(rest, mapper);

        ReflectionTestUtils.setField(c, "apiUrl", "http://example.com");
        ReflectionTestUtils.setField(c, "username", "u");
        ReflectionTestUtils.setField(c, "apiKey", "k");
        ReflectionTestUtils.setField(c, "database", "db");

        String response = "{\"result\":{\"uid\":321}}";
        when(rest.postForObject(contains("/web/session/authenticate"), any(), eq(String.class))).thenReturn(response);

        int uid = c.authenticate();
        assertEquals(321, uid);

        // call again; since uid>0 and expiration set in the future, it should return cached uid
        int uid2 = c.authenticate();
        assertEquals(321, uid2);

        // rest.postForObject should have been called only once
        verify(rest, times(1)).postForObject(contains("/web/session/authenticate"), any(), eq(String.class));
    }

    @Test
    void testCrmCallSuccess() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CrmJsonRpcConnector connector = new CrmJsonRpcConnector(restTemplate, objectMapper);

        // configure fields
        ReflectionTestUtils.setField(connector, "apiUrl", "http://example.com");
        ReflectionTestUtils.setField(connector, "username", "u");
        ReflectionTestUtils.setField(connector, "apiKey", "k");
        ReflectionTestUtils.setField(connector, "database", "db");
        // set uid and expiration so authenticate() is a noop
        ReflectionTestUtils.setField(connector, "uid", 1);
        ReflectionTestUtils.setField(connector, "expiration", System.currentTimeMillis() + 10000L);

        String jsonResponse = "{\"jsonrpc\":\"2.0\",\"result\":{\"ok\":true},\"id\":1}";
        when(restTemplate.postForObject(eq("http://example.com/jsonrpc"), any(), eq(String.class))).thenReturn(jsonResponse);

        Map<String, Object> params = new HashMap<>();
        params.put("hello", "world");

        Object result = connector.crmCall("/jsonrpc", "call", params);
        assertNotNull(result);
        assertTrue(result instanceof Map);
        Map<?,?> m = (Map<?,?>) result;
        assertEquals(Boolean.TRUE, m.get("ok"));
    }

    @Test
    void testCrmCallFailureThrows() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CrmJsonRpcConnector connector = new CrmJsonRpcConnector(restTemplate, objectMapper);

        ReflectionTestUtils.setField(connector, "apiUrl", "http://example.com");
        ReflectionTestUtils.setField(connector, "uid", 1);
        ReflectionTestUtils.setField(connector, "expiration", System.currentTimeMillis() + 10000L);

        when(restTemplate.postForObject(eq("http://example.com/jsonrpc"), any(), eq(String.class))).thenThrow(new RuntimeException("network"));

        Map<String, Object> params = new HashMap<>();
        params.put("x", "y");

        assertThrows(RuntimeException.class, () -> connector.crmCall("/jsonrpc", "call", params));
    }

    @Test
    void testCrmCallExecuteKwSuccess() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CrmJsonRpcConnector connector = new CrmJsonRpcConnector(restTemplate, objectMapper);

        ReflectionTestUtils.setField(connector, "apiUrl", "http://example.com");
        ReflectionTestUtils.setField(connector, "uid", 1);
        ReflectionTestUtils.setField(connector, "expiration", System.currentTimeMillis() + 10000L);
        ReflectionTestUtils.setField(connector, "database", "db");
        ReflectionTestUtils.setField(connector, "apiKey", "k");

        String jsonResponse = "{\"jsonrpc\":\"2.0\",\"result\": [ {\"id\":1} ],\"id\":1 }";
        when(restTemplate.postForObject(eq("http://example.com/jsonrpc"), any(), eq(String.class))).thenReturn(jsonResponse);

        Object res = connector.crmCallExecuteKw("res.partner", "search_read", new Object[] {}, Map.of("fields", new Object[]{"name"}));
        assertNotNull(res);
        assertTrue(res instanceof java.util.List);
    }

    @Test
    void testCrmCallExecuteKwFailure() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CrmJsonRpcConnector connector = new CrmJsonRpcConnector(restTemplate, objectMapper);

        ReflectionTestUtils.setField(connector, "apiUrl", "http://example.com");
        ReflectionTestUtils.setField(connector, "uid", 1);
        ReflectionTestUtils.setField(connector, "expiration", System.currentTimeMillis() + 10000L);

        when(restTemplate.postForObject(eq("http://example.com/jsonrpc"), any(), eq(String.class))).thenThrow(new RuntimeException("net"));

        assertThrows(RuntimeException.class, () -> connector.crmCallExecuteKw("res.partner", "search_read", new Object[]{}, Map.of()));
    }
}
