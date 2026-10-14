package org.simexid.odoo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simexid.odoo.model.SearchRequest;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/** The generic /search endpoint: opt-in per model, capped, and behind the same key as everything else. */
class CrmControllerSearchTest {

    private CrmServiceImpl service;
    private CrmController controller;

    @BeforeEach
    void setUp() {
        service = mock(CrmServiceImpl.class);
        controller = new CrmController(service);
        ReflectionTestUtils.setField(controller, "apiKeys", new String[]{"k"});
        ReflectionTestUtils.setField(controller, "searchModels", new String[]{"subscription.contract", " res.partner "});
        ReflectionTestUtils.setField(controller, "searchMaxLimit", 200);
    }

    private static SearchRequest request(String model, Integer limit) {
        return new SearchRequest(model, List.of(List.of("partner_id", "=", 1)), List.of("name"), limit, null, null);
    }

    @Test
    void anAllowedModelIsSearchedWithTheCallersDomainAndFields() throws Exception {
        when(service.searchRead(any(), any(), any(), any(), any(), any())).thenReturn(List.of(Map.of("name", "SUB/1")));

        ResponseEntity<Object> response = controller.search(request("subscription.contract", 10), "k", null);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(List.of(Map.of("name", "SUB/1")), response.getBody());
        verify(service).searchRead(eq("subscription.contract"), any(), eq(List.of("name")), eq(10), eq(0), any());
    }

    @Test
    void modelsAreMatchedAfterTrimmingTheConfiguredList() throws Exception {
        when(service.searchRead(any(), any(), any(), any(), any(), any())).thenReturn(List.of());

        assertEquals(200, controller.search(request("res.partner", null), "k", null).getStatusCode().value());
    }

    @Test
    void aModelOutsideTheListIsForbidden() throws Exception {
        ResponseEntity<Object> response = controller.search(request("res.users", 5), "k", null);

        assertEquals(403, response.getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void withNoModelsConfiguredTheEndpointIsOffEvenWithAValidKey() throws Exception {
        ReflectionTestUtils.setField(controller, "searchModels", new String[]{});

        assertEquals(403, controller.search(request("res.partner", 5), "k", null).getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void aWrongKeyIsUnauthorizedBeforeAnythingElseIsChecked() throws Exception {
        assertEquals(401, controller.search(request("res.users", 5), "bad", null).getStatusCode().value());
        assertEquals(401, controller.search(request("subscription.contract", 5), null, null).getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void fieldsAreRequiredSoWholeRecordsAreNeverPulledByAccident() throws Exception {
        var noFields = new SearchRequest("subscription.contract", List.of(), List.of(), null, null, null);

        assertEquals(400, controller.search(noFields, "k", null).getStatusCode().value());
        verify(service, never()).searchRead(any(), any(), any(), any(), any(), any());
    }

    @Test
    void theLimitIsDefaultedAndCapped() throws Exception {
        when(service.searchRead(any(), any(), any(), anyInt(), anyInt(), any())).thenReturn(List.of());

        controller.search(request("subscription.contract", 100_000), "k", null);
        verify(service).searchRead(any(), any(), any(), eq(200), eq(0), any());

        controller.search(request("subscription.contract", null), "k", null);
        verify(service).searchRead(any(), any(), any(), eq(100), eq(0), any());
    }

    @Test
    void aMalformedDomainIsABadRequestAndAnOdooFailureIsAnInternalError() throws Exception {
        when(service.searchRead(any(), any(), any(), any(), any(), any()))
                .thenThrow(new IllegalArgumentException("null is not allowed in a domain"))
                .thenThrow(new RuntimeException("odoo down"));

        assertEquals(400, controller.search(request("subscription.contract", 5), "k", null).getStatusCode().value());
        assertEquals(500, controller.search(request("subscription.contract", 5), "k", null).getStatusCode().value());
    }
}
