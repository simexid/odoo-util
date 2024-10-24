package org.simexid.odoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

/**
 * Odoo model class for SaleOrderLine received from webhook
 */
public class SaleOrderLineHook {

    @JsonProperty("_action")
    private String action;

    @JsonProperty("_id")
    private int id;

    @JsonProperty("_model")
    private String model;

    @JsonProperty("linked_line_id")
    private boolean linkedLineId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_short")
    private String nameShort;

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("order_partner_id")
    private int orderPartnerId;

    @JsonProperty("price_total")
    private double priceTotal;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("product_template_id")
    private int productTemplateId;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("product_uom_category_id")
    private int productUomCategoryId;

    // Getter e Setter

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isLinkedLineId() {
        return linkedLineId;
    }

    public void setLinkedLineId(boolean linkedLineId) {
        this.linkedLineId = linkedLineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderPartnerId() {
        return orderPartnerId;
    }

    public void setOrderPartnerId(int orderPartnerId) {
        this.orderPartnerId = orderPartnerId;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductTemplateId() {
        return productTemplateId;
    }

    public void setProductTemplateId(int productTemplateId) {
        this.productTemplateId = productTemplateId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getProductUomCategoryId() {
        return productUomCategoryId;
    }

    public void setProductUomCategoryId(int productUomCategoryId) {
        this.productUomCategoryId = productUomCategoryId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
