package org.simexid.odoo;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.simexid.odoo.connector.CrmXmlRpcConnector;
import org.simexid.odoo.controller.CrmController;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration class for Simexid Odoo integration.
 */
@AutoConfiguration
@EnableConfigurationProperties(SimexidOdooConfigurationProperties.class)
public class SimexidOdooAutoConfiguration {

    @Bean
    public CrmServiceImpl crmService(XmlRpcClient xmlRpcClient) {
        return new CrmServiceImpl(xmlRpcClient);
    }

    @Bean
    public CrmController crmController(CrmServiceImpl crmService) {
        return new CrmController(crmService);
    }

    @Bean
    public CrmXmlRpcConnector crmXmlRpcConnector() {
        return new CrmXmlRpcConnector();
    }
}