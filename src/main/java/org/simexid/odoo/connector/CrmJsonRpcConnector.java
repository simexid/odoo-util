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
        // default convenience: call generic JSON-RPC endpoint with empty params
        try {
            return crmCall("/jsonrpc", "call", new HashMap<>());
        } catch (Exception e) {
            throw new RuntimeException("crmCall failed", e);
        }
    }

    /**
     * Perform a JSON-RPC call against the configured apiUrl + path.
     * @param path endpoint path (for example "/jsonrpc")
     * @param method JSON-RPC method name (usually "call")
     * @param params parameters map to send as "params"
     * @return the "result" object from the JSON-RPC response or the full response map if no result key
     * @throws Exception on serialization/network errors
     */
    public Object crmCall(String path, String method, Map<String, Object> params) throws Exception {
        // ensure we have a session (authenticate manages uid/expiration)
        authenticate();

        Map<String, Object> payload = new HashMap<>();
        payload.put("jsonrpc", "2.0");
        payload.put("method", method);
        payload.put("params", params != null ? params : new HashMap<>());
        payload.put("id", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonPayload = objectMapper.writeValueAsString(payload);
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        String url = apiUrl != null ? apiUrl + path : path;
        String jsonResponse = restTemplate.postForObject(url, request, String.class);

        // parse response
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
        if (responseMap.containsKey("result")) {
            return responseMap.get("result");
        }
        return responseMap;
    }

    /**
     * Convenience to call Odoo's execute_kw via JSON-RPC (service object -> execute_kw)
     * @param model model name (e.g. "res.partner")
     * @param methodName method to invoke (e.g. "search_read")
     * @param args positional args for the method (e.g. domain)
     * @param kwargs keyword args for the method (e.g. {"fields": [...]})
     * @return result from the JSON-RPC response
     */
    public Object crmCallExecuteKw(String model, String methodName, Object[] args, Map<String, Object> kwargs) throws Exception {
        Map<String, Object> callParams = new HashMap<>();
        callParams.put("service", "object");
        callParams.put("method", "execute_kw");
        // args for execute_kw: [db, uid, password, model, method, args, kwargs]
        Object[] callArgs = new Object[]{database, uid, apiKey, model, methodName, args != null ? args : new Object[]{}, kwargs != null ? kwargs : new HashMap<>()};
        callParams.put("args", callArgs);
        return crmCall("/jsonrpc", "call", callParams);
    }

}
