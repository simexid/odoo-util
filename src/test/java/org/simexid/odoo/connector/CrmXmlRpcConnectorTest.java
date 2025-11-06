package org.simexid.odoo.connector;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrmXmlRpcConnectorTest {

    @Test
    void testXmlRpcClientBeanCreation() throws Exception {
        CrmXmlRpcConnector connector = new CrmXmlRpcConnector();
        // set fields via reflection
        org.springframework.test.util.ReflectionTestUtils.setField(connector, "apiUrl", "http://example.com/xmlrpc/2/");
        org.springframework.test.util.ReflectionTestUtils.setField(connector, "username", "u");
        org.springframework.test.util.ReflectionTestUtils.setField(connector, "apiKey", "k");

        XmlRpcClient client = connector.xmlRpcClient();
        assertNotNull(client);
    }
}
