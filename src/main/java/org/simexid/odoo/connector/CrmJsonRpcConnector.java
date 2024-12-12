package org.simexid.odoo.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrmJsonRpcConnector {

    @Value("${simexid.crm.odoo-json-url}")
    private String apiUrl;

    @Value("${simexid.crm.odoo-username}")
    private String username;

    @Value("${simexid.crm.odoo-api-key}")
    private String apiKey;

    @Value("${simexid.crm.odoo-db}")
    private String database;

    private int uid;
    private long expiration;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CrmJsonRpcConnector(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public int authenticate() {
        long now = new Date().getTime();
        if (uid >0 && now<expiration) {
            return uid;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> params = new HashMap<>();
        params.put("db", database);
        params.put("login", username);
        params.put("password", apiKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("jsonrpc", "2.0");
        payload.put("method", "call");
        payload.put("params", params);
        payload.put("id", 1);

        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String jsonResponse = restTemplate.postForObject(apiUrl + "/web/session/authenticate", request, String.class);

            Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> result = (Map<String, Object>) responseMap.get("result");
            uid = (Integer) result.get("uid");
            expiration = new Date().getTime() + 7200000;
            return uid;

        } catch (Exception e) {
            return 0;
        }
    }

    public Object crmCall() {
        return null;
    }

}
