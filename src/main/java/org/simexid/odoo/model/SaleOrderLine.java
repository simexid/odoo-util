package org.simexid.odoo.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Odoo model class for SaleOrderLine
 */
public class SaleOrderLine {

    @SerializedName("price_unit")
    private double priceUnit;

    @SerializedName("price_subtotal")
    private double priceSubtotal;

    @SerializedName("product_id")
    private Product productId;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("product_template_id")
    private int productTemplateId;

    @SerializedName("order_partner_id")
    private int orderPartnerId;

    private String name;
    private double discount;
    private int id;
    private String state;

    @SerializedName("product_uom_qty")
    private int productUomQty;

    @SerializedName("price_total")
    private double priceTotal;


    public double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(double priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getProductUomQty() {
        return productUomQty;
    }

    public void setProductUomQty(int productUomQty) {
        this.productUomQty = productUomQty;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getProductTemplateId() {
        return productTemplateId;
    }

    public void setProductTemplateId(int productTemplateId) {
        this.productTemplateId = productTemplateId;
    }

    public int getOrderPartnerId() {
        return orderPartnerId;
    }

    public void setOrderPartnerId(int orderPartnerId) {
        this.orderPartnerId = orderPartnerId;
    }

    public static class Product {
        private int id;
        private String name;

        public Product(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
