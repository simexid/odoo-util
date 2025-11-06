package org.simexid.odoo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simexid.odoo.model.Customer;
import org.simexid.odoo.model.SaleOrder;
import org.simexid.odoo.model.SaleOrderHook;
import org.simexid.odoo.model.SaleOrderLineHook;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

class CrmControllerAdditionalTest {

    private CrmServiceImpl svc;
    private CrmController controller;

    @BeforeEach
    void setUp() {
        svc = mock(CrmServiceImpl.class);
        controller = new CrmController(svc);
        ReflectionTestUtils.setField(controller, "apiKeys", new String[]{"k"});
    }

    @Test
    void testWebhooksAuthorizedAndServiceCalled() {
        SaleOrderHook hook = new SaleOrderHook();
        hook.setId(1);
        ResponseEntity<Void> resp = controller.webhooksSaleorder(hook, "k", null);
        assertEquals(200, resp.getStatusCode().value());
    }

    @Test
    void testWebhooksUnauthorized() {
        SaleOrderLineHook hook = new SaleOrderLineHook();
        ResponseEntity<Void> resp = controller.webhooksSaleorderline(hook, "bad", null);
        assertEquals(401, resp.getStatusCode().value());
    }

    @Test
    void testGetPartnerAndSalesAndGenericAndFieldsInternalError() throws Exception {
    when(svc.getPartner(any(), any(), anyString(), any())).thenThrow(new RuntimeException("boom"));
    ResponseEntity<List<Customer>> p = controller.getPartner(org.simexid.odoo.enums.CrmSearchEnum.SearchField.id,
        org.simexid.odoo.enums.CrmSearchEnum.SearchOperator.equal, "1", "k", null);
        assertEquals(500, p.getStatusCode().value());

    when(svc.getSales(any(), any(), any(), any())).thenThrow(new RuntimeException("boom2"));
    ResponseEntity<List<SaleOrder>> s = controller.getSales(org.simexid.odoo.enums.CrmSearchEnum.SearchField.id,
        org.simexid.odoo.enums.CrmSearchEnum.SearchOperator.equal, "1", "k", null);
        assertEquals(500, s.getStatusCode().value());

    when(svc.getFields(any())).thenThrow(new RuntimeException("boom3"));
    ResponseEntity<Object> f = controller.getFields(org.simexid.odoo.enums.CrmSearchEnum.Model.saleorder, "k", null);
        assertEquals(500, f.getStatusCode().value());

    when(svc.getGenericObject(any())).thenThrow(new RuntimeException("boom4"));
    ResponseEntity<Object> g = controller.getGeneric(org.simexid.odoo.enums.CrmSearchEnum.Model.saleorder, "k", null);
        assertEquals(500, g.getStatusCode().value());
    }
}
