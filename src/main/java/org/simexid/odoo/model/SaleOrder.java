package org.simexid.odoo.model;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Odoo model class for SaleOrder
 */
public class SaleOrder {
    @SerializedName("partner_id")
    private Partner partnerId;
    @SerializedName("date_order")
    private String dateOrder;
    @SerializedName("order_line")
    private List<Integer> orderLine;
    private List<SaleOrderLine> orderLineObject;
    private List<SaleOrderLine> commentLines;
    private String name;
    @SerializedName("amount_total")
    private double amountTotal;
    @SerializedName("partner_invoice_id")
    private Partner partnerInvoiceId;
    private int id;
    private String state;
    @SerializedName("partner_shipping_id")
    private Partner partnerShippingId;

    public Partner getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Partner partnerId) {
        this.partnerId = partnerId;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public List<Integer> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<Integer> orderLine) {
        this.orderLine = orderLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Partner getPartnerInvoiceId() {
        return partnerInvoiceId;
    }

    public void setPartnerInvoiceId(Partner partnerInvoiceId) {
        this.partnerInvoiceId = partnerInvoiceId;
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

    public Partner getPartnerShippingId() {
        return partnerShippingId;
    }

    public void setPartnerShippingId(Partner partnerShippingId) {
        this.partnerShippingId = partnerShippingId;
    }

    public List<SaleOrderLine> getOrderLineObject() {
        return orderLineObject;
    }

    public void setOrderLineObject(List<SaleOrderLine> orderLineObject) {
        this.orderLineObject = orderLineObject;
    }

    public List<SaleOrderLine> getCommentLines() {
        return commentLines;
    }

    public void setCommentLines(List<SaleOrderLine> commentLines) {
        this.commentLines = commentLines;
    }

    public static class Partner {
        private int id;
        private String name;

        public Partner(int id, String name) {
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
