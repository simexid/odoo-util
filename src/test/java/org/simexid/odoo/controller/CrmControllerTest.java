package org.simexid.odoo.controller;

import org.junit.jupiter.api.Test;
import org.simexid.odoo.model.Customer;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrmControllerTest {

    @Test
    void testGetPartnersUnauthorized() {
        CrmServiceImpl svc = mock(CrmServiceImpl.class);
        CrmController controller = new CrmController(svc);
        // set apiKeys to a different value -> unauthorized
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "apiKeys", new String[]{"secret"});
        ResponseEntity<List<Customer>> resp = controller.getPartners("invalid");
        assertEquals(401, resp.getStatusCode().value());
    }

    @Test
    void testGetPartnersAuthorized() throws Exception {
        CrmServiceImpl svc = mock(CrmServiceImpl.class);
        when(svc.getAllPartners(null)).thenReturn(List.of(new Customer()));
        CrmController controller = new CrmController(svc);
        // set apiKeys field to contain the key
        ReflectionTestUtils.setField(controller, "apiKeys", new String[]{"secret"});
        ResponseEntity<List<Customer>> resp = controller.getPartners("secret");
        assertEquals(200, resp.getStatusCode().value());
        assertEquals(1, resp.getBody().size());
    }
}
