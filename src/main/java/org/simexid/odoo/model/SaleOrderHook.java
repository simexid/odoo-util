package org.simexid.odoo.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.List;

/**
 * Odoo model class for SaleOrder received from webhook
 */
public class SaleOrderHook {

    @JsonProperty("_action")
    private String action;

    @JsonProperty("_id")
    private int id;

    @JsonProperty("_model")
    private String model;

    @JsonProperty("amount_paid")
    private double amountPaid;

    @JsonProperty("amount_total")
    private double amountTotal;

    @JsonProperty("date_order")
    private String dateOrder;

    @JsonProperty("order_line")
    private List<Object> orderLine;  // Adatta il tipo se hai una struttura specifica per `order_line`

    @JsonProperty("partner_id")
    private int partnerId;

    // Getter e setter

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

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public List<Object> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<Object> orderLine) {
        this.orderLine = orderLine;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
