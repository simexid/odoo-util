package org.simexid.odoo.controller;

import org.simexid.odoo.enums.CrmSearchEnum;
import org.simexid.odoo.model.*;
import org.simexid.odoo.service.impl.CrmServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for Odoo CRM query operations
 */
@RestController
@RequestMapping("${simexid.crm.api-path}")
@ConditionalOnProperty(name = "simexid.crm.api-enabled", havingValue = "true", matchIfMissing = false)
public class CrmController {

    private final CrmServiceImpl crmService;

    @Value("${simexid.crm.api-keys}")
    private String[] apiKeys;

    public CrmController(CrmServiceImpl crmService) {
        this.crmService = crmService;
    }

    /**
     * Sale order webhooks
     * @param input SaleOrderHook object
     * @param key API key
     * @return StatusCode 200 if successful, 401 if unauthorized, 500 if internal server error
     * the api is used for intercept odoo webhooks for a sale order
     */
    @PostMapping("/webhooks/saleorder")
    public ResponseEntity<Void> webhooksSaleorder(@RequestBody SaleOrderHook input, @RequestParam("apikey") String key) {
        return webhooksAction(input, key);
    }

    /**
     * Sale order line webhooks
     * @param input SaleOrderLineHook object
     * @param key API key
     * @return StatusCode 200 if successful, 401 if unauthorized, 500 if internal server error
     * the api is used for intercept odoo webhooks for a sale order line
     */
    @PostMapping("/webhooks/saleorderline")
    public ResponseEntity<Void> webhooksSaleorderline(@RequestBody SaleOrderLineHook input, @RequestParam("apikey") String key) {
        return webhooksAction(input, key);
    }

    /**
     * Customer webhooks
     * @param input CustomerHook object
     * @param key API key
     * @return StatusCode 200 if successful, 401 if unauthorized, 500 if internal server error
     * the api is used for intercept odoo webhooks for a customer
     */
    @PostMapping("/webhooks/partner")
    public ResponseEntity<Void> webhooksPartner(@RequestBody CustomerHook input, @RequestParam("apikey") String key) {
        return webhooksAction(input, key);
    }

    /**
     * Generic webhooks
     * @param input Object
     * @param key API key
     * @return StatusCode 200 if successful, 401 if unauthorized, 500 if internal server error
     * the api is used for intercept odoo webhooks for a generic object
     */
    @PostMapping("/webhooks/generic")
    public ResponseEntity<Void> webhooksGeneric(@RequestBody Object input, @RequestParam("apikey") String key) {
        return webhooksAction(input, key);
    }

    /**
     * Get all partners
     * @param key API key
     * @return List of Customer objects
     * @return StatusCode 200 if successful and a list of partners/customer, 401 if unauthorized, 500 if internal server error
     */
    @GetMapping("/partners")
    public ResponseEntity<List<Customer>> getPartners(@RequestParam("apikey") String key) {
        if (!checkApiKey(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            return ResponseEntity.ok(crmService.getAllPartners(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get partner
     * @param field SearchField enum
     * @param criteria SearchOperator enum
     * @param search Search criteria
     * @param key API key
     * @return StatusCode 200 if successful and a list of partners/customer for the search criteria, 401 if unauthorized, 500 if internal server error
     */
    @GetMapping("/partner/{field}/{criteria}/{search}")
    public ResponseEntity<List<Customer>> getPartner(@PathVariable("field") CrmSearchEnum.SearchField field, @PathVariable("criteria") CrmSearchEnum.SearchOperator criteria,
                                                     @PathVariable("search") String search, @RequestParam("apikey") String key) {
        if (!checkApiKey(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            return ResponseEntity.ok(crmService.getPartner(field, criteria, search, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get sales
     * @param field SearchField enum
     * @param criteria SearchOperator enum
     * @param search Search criteria
     * @param key API key
     * @return StatusCode 200 if successful and a list of sales for the search criteria, 401 if unauthorized, 500 if internal server error
     */
    @GetMapping("/sales/{field}/{criteria}/{search}")
    public ResponseEntity<List<SaleOrder>> getSales(@PathVariable("field") CrmSearchEnum.SearchField field, @PathVariable("criteria") CrmSearchEnum.SearchOperator criteria,
                                                    @PathVariable("search") Object search, @RequestParam("apikey") String key) {
        if (!checkApiKey(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            return ResponseEntity.ok(crmService.getSales(field, criteria, search, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get fields
     * @param model Model enum
     * @param key API key
     * @return StatusCode 200 if successful and a list of fields for the model, 401 if unauthorized, 500 if internal server error
     */
    @GetMapping("fields/search/{model}")
    public ResponseEntity<Object> getFields(@PathVariable("model") CrmSearchEnum.Model model, @RequestParam("apikey") String key) {
        if (!checkApiKey(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            return ResponseEntity.ok(crmService.getFields(model));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get generic object
     * @param model Model enum
     * @param key API key
     * @return StatusCode 200 if successful and a generic object for the model, 401 if unauthorized, 500 if internal server error
     */
    @GetMapping("generic/search/{model}")
    public ResponseEntity<Object> getGeneric(@PathVariable("model") CrmSearchEnum.Model model, @RequestParam("apikey") String key) {
        if (!checkApiKey(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            return ResponseEntity.ok(crmService.getGenericObject(model));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Check API key
     * @param key API key
     * @return true if key is valid, false otherwise
     */
    private boolean checkApiKey(String key) {
        return Arrays.stream(apiKeys).findFirst().filter(key::equals).isPresent();
    }

    /**
     * Webhooks action
     * @param input Object
     * @param key API key
     * @return StatusCode 200 if successful, 401 if unauthorized, 500 if internal server error
     * the api is used for intercept odoo webhooks, call the service method to save the webhook and give action
     * the give action method must be implemented in the service class. Override the method for your own implementation
     */
    private ResponseEntity<Void> webhooksAction(Object input, String key) {
        if (!checkApiKey(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            crmService.saveWebhook(input.toString());
            crmService.giveAction(input);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
