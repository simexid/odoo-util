package org.simexid.odoo.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
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
}
