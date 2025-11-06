package org.simexid.odoo;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.simexid.odoo.controller.CrmController;
import org.simexid.odoo.connector.CrmXmlRpcConnector;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimexidOdooAutoConfiguration.class)
@TestPropertySource(properties = {
        "simexid.crm.api-enabled=true",
        "simexid.crm.api-path=/api",
        "simexid.crm.odoo-api-url=http://localhost/",
        "simexid.crm.odoo-username=user",
        "simexid.crm.odoo-api-key=key"
})
class SimexidOdooAutoConfigurationIntegrationTest {

    @Autowired(required = false)
    CrmServiceImpl crmService;

    @Autowired(required = false)
    CrmController crmController;

    @Autowired(required = false)
    CrmXmlRpcConnector connector;

    @MockBean
    XmlRpcClient xmlRpcClient;

    @Test
    void contextLoadsAndBeansPresent() {
        assertNotNull(crmService);
        assertNotNull(crmController);
        assertNotNull(connector);
        assertNotNull(xmlRpcClient);
    }
}
