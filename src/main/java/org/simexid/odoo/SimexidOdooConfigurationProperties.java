package org.simexid.odoo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for Simexid Keycloak integration.
 */
@Component
@PropertySource("classpath:odoo-util.properties")
@ConfigurationProperties(prefix = "simexid.crm")
public class SimexidOdooConfigurationProperties {

    /**
     * The URL of the Odoo instance.
     */
    String odooApiUrl;
    /**
     * The username to use for the Odoo API. Check rules for the user
     */
    String odooUsername;
    /**
     * The API key to use for the Odoo API. Generated from profile page
     */
    String odooApiKey;
    /**
     * The database name to use for the Odoo API.
     */
    String odooDb;
    /**
     * Whether the API is enabled or not. Thi enable the spring controller and make the services available
     */
    Boolean apiEnabled;
    /**
     * The path to the API. This is the base path for the API
     */
    String apiPath;
    /**
     * The API keys to use for the API. This is used to authenticate the API requests
     */
    String[] apyKeys;

    public String getOdooApiUrl() {
        return odooApiUrl;
    }

    public void setOdooApiUrl(String odooApiUrl) {
        this.odooApiUrl = odooApiUrl;
    }

    public String getOdooUsername() {
        return odooUsername;
    }

    public void setOdooUsername(String odooUsername) {
        this.odooUsername = odooUsername;
    }

    public String getOdooApiKey() {
        return odooApiKey;
    }

    public void setOdooApiKey(String odooApiKey) {
        this.odooApiKey = odooApiKey;
    }

    public String getOdooDb() {
        return odooDb;
    }

    public void setOdooDb(String odooDb) {
        this.odooDb = odooDb;
    }

    public Boolean getApiEnabled() {
        return apiEnabled;
    }

    public void setApiEnabled(Boolean apiEnabled) {
        this.apiEnabled = apiEnabled;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String[] getApyKeys() {
        return apyKeys;
    }

    public void setApyKeys(String[] apyKeys) {
        this.apyKeys = apyKeys;
    }
}
