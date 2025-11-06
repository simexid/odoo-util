package org.simexid.odoo.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.simexid.odoo.enums.CrmSearchEnum;
import org.simexid.odoo.model.Customer;
import org.simexid.odoo.model.SaleOrder;
import org.simexid.odoo.model.SaleOrderLine;
import org.simexid.odoo.model.deserializer.PartnerDeserializer;
import org.simexid.odoo.model.deserializer.ProductDeserializer;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Collections.emptyMap;

/**
 * Service class for Odoo CRM query operations
 */
@Service
public class CrmServiceImpl {

    @Value("${simexid.crm.odoo-api-url}")
    private String apiUrl;

    @Value("${simexid.crm.odoo-username}")
    private String username;

    @Value("${simexid.crm.odoo-api-key}")
    private String apiKey;

    @Value("${simexid.crm.odoo-db}")
    private String db;

    private int uid;
    private long expiration;

    private static final Logger logger = LoggerFactory.getLogger(CrmServiceImpl.class);

    private final XmlRpcClient xmlRpcClient;

    @Autowired
    public CrmServiceImpl(XmlRpcClient xmlRpcClient) {
        this.xmlRpcClient = xmlRpcClient;
    }

    public int authenticate() throws Exception {
        long now = new Date().getTime();
        if (uid > 0 && now < expiration) {
            return uid;
        }
        XmlRpcClientConfigImpl config = (XmlRpcClientConfigImpl) xmlRpcClient.getConfig();
        config.setServerURL(new java.net.URL(apiUrl + "common"));
        xmlRpcClient.setConfig(config);
        Object[] params = new Object[]{db, username, apiKey, emptyMap()};
        uid = (int) safeExecute("authenticate", params);
        expiration = new Date().getTime() + 7200000;
        return uid;
    }

    public List<Customer> getAllPartners(Object[] fields) throws Exception {
        setEndpoint("object");
        if (fields == null) {
            fields = new Object[]{"name", "email", "phone", "mobile", "street", "city", "zip", "country_id", "vat"};
        }
        Object[] finalFields = fields;
        Object[] params = new Object[]{db, uid, apiKey,
                "res.partner",
                "search_read",
                new Object[]{},
                new HashMap<String, Object>() {{
                    put("fields", finalFields);
                }}
        };
        Object result = safeExecute("execute_kw", params);
        Gson gson = new Gson();
        Type customerListType = new TypeToken<List<Customer>>(){}.getType();
        return gson.fromJson(gson.toJson(result), customerListType);
    }

    public List<Customer> getPartner(CrmSearchEnum.SearchField searchField, CrmSearchEnum.SearchOperator searchOperator,
                                     Object search, Object[] fields) throws Exception {
        setEndpoint("object");
        search = isNumeric(search);
        Object[] searchCriteria = setSearchCriteria(searchField, searchOperator, search);
        if (fields == null) {
            fields = new Object[]{"name", "email", "phone", "mobile", "street", "city", "zip", "country_id", "vat"};
        }
        Object[] finalFields = fields;
        Object[] params = new Object[]{db, uid, apiKey,
                "res.partner",
                "search_read",
                searchCriteria,
                new HashMap<String, Object>() {{
                    put("fields", finalFields);
                }}
        };
        Object result = safeExecute("execute_kw", params);
        Gson gson = new Gson();
        Type customerListType = new TypeToken<List<Customer>>(){}.getType();
        return gson.fromJson(gson.toJson(result), customerListType);
    }

    public List<SaleOrder> getSales(CrmSearchEnum.SearchField searchField, CrmSearchEnum.SearchOperator searchOperator,
                                    Object search, Object[] fields) throws Exception {
        setEndpoint("object");
        search = isNumeric(search);
        Object[] searchCriteria = setSearchCriteria(searchField, searchOperator, search);

        if (fields == null) {
            fields = new Object[]{"name", "date_order", "amount_total", "state", "order_line", "partner_id", "partner_invoice_id", "partner_shipping_id"};
        }
        Object[] finalFields = fields;
        Object[] params = new Object[]{db, uid, apiKey,
                "sale.order",
                "search_read",
                searchCriteria,
                new HashMap<String, Object>() {{
                    put("fields", finalFields);
                }}
        };
        Object result = safeExecute("execute_kw", params);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SaleOrder.Partner.class, new PartnerDeserializer());
        Gson gson = gsonBuilder.create();
        Type saleOrderListType = new TypeToken<List<SaleOrder>>(){}.getType();
        List<SaleOrder> saleOrders = gson.fromJson(gson.toJson(result), saleOrderListType);
        saleOrders.forEach(order -> {
            try {
                List<SaleOrderLine> lines = getOrderLine(order.getOrderLine());
                List<SaleOrderLine> commentLines = lines.stream().filter(line -> line.getProductId().getId() == 0 || line.getPriceTotal() == 0).toList();
                order.setCommentLines(commentLines);
                order.setOrderLineObject(lines.stream().filter(line -> line.getProductId().getId() != 0 && line.getPriceTotal() > 0).toList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return saleOrders;
    }

    public List<SaleOrderLine> getOrderLine(List<Integer> orderLineIds) throws Exception {
        setEndpoint("object");
        List<String> orderLineFields = List.of("name", "state", "product_id", "product_uom_qty", "price_unit", "price_subtotal", "discount", "price_total");
        List<Object> domain = new ArrayList<>();
        domain.add(Arrays.asList("id", "in", orderLineIds));

        Object result = safeExecute("execute_kw", new Object[]{
                db, uid, apiKey,
                "sale.order.line", "search_read",
                new Object[]{domain},
                new HashMap<String, Object>() {{
                    put("fields", orderLineFields);
                }}
        });
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SaleOrderLine.Product.class, new ProductDeserializer());
        Gson gson = gsonBuilder.create();
        Type saleOrderLineListType = new TypeToken<List<SaleOrderLine>>(){}.getType();
        return gson.fromJson(gson.toJson(result), saleOrderLineListType);
    }

    public Object getFields(CrmSearchEnum.Model search) throws Exception {
        setEndpoint("object");
        return safeExecute("execute_kw", new Object[]{
                db, uid, apiKey,
                search.getModel(), "fields_get",
                new Object[]{},
                new HashMap<String, Object>() {{
                    put("attributes", new Object[]{"string", "help", "type"});
                }}
        });
    }

    public Object getGenericObject(CrmSearchEnum.Model search) throws Exception {
        setEndpoint("object");

        Map<String, Object> fields = (Map<String, Object>) getFields(search);
        List<String> fieldNames = new ArrayList<>(fields.keySet());

        return safeExecute("execute_kw", new Object[]{
                db, uid, apiKey,
                search.getModel(), "search_read",
                new Object[]{},
                new HashMap<String, Object>() {{
                    put("fields", fieldNames.toArray());
                }}
        });
    }

    public void saveWebhook(String json) {
        // default no-op; override in applications
    }

    public void giveAction(Object input) {
        // default no-op; override in applications
    }

    private void setEndpoint(String endpoint) throws Exception {
        authenticate();
        XmlRpcClientConfigImpl config = (XmlRpcClientConfigImpl) xmlRpcClient.getConfig();
        config.setServerURL(new java.net.URL(apiUrl + endpoint));
        xmlRpcClient.setConfig(config);
    }

    private Object[] setSearchCriteria(CrmSearchEnum.SearchField searchField, CrmSearchEnum.SearchOperator searchOperator, Object search) {
        return new Object[]{
                new Object[]{
                        new Object[]{searchField.getFieldName(), searchOperator.getOperator(), search}
                }
        };
    }

    public static Object isNumeric(Object str) {
        if (str == null) {
            throw new IllegalArgumentException("Search cannot be null");
        }
        if (str instanceof Integer) {
            return str;
        }
        try {
            return Integer.parseInt((String) str);
        } catch (NumberFormatException e) {
            return str;
        }
    }

    /**
     * Execute XML-RPC call and convert exceptions to runtime with logging.
     */
    private Object safeExecute(String methodName, Object[] params) throws Exception {
        try {
            return xmlRpcClient.execute(methodName, params);
        } catch (Exception e) {
            logger.error("Error executing xmlrpc method {}", methodName, e);
            throw e;
        }
    }
}
