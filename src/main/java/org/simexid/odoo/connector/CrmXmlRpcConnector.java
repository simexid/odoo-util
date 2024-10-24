package org.simexid.odoo.connector;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.net.URL;

@AutoConfiguration
public class CrmXmlRpcConnector {

    @Value("${simexid.crm.odoo-api-url}")
    private String apiUrl;

    @Value("${simexid.crm.odoo-username}")
    private String username;

    @Value("${simexid.crm.odoo-api-key}")
    private String apiKey;

    @Bean
    public XmlRpcClient xmlRpcClient() throws Exception {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(apiUrl));
        config.setBasicUserName(username);
        config.setBasicPassword(apiKey);
        config.setEnabledForExtensions(true);
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        return client;
    }
}
