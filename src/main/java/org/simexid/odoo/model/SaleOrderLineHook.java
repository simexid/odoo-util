package org.simexid.odoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.List;

/**
 * Odoo model class for SaleOrderLine received from webhook
 */
public class SaleOrderLineHook {

    @JsonProperty("_action")
    private String action;

    @JsonProperty("_id")
    private Integer id;

    @JsonProperty("_model")
    private String model;

    @JsonProperty("analytic_distribution")
    private boolean analyticDistribution;

    @JsonProperty("analytic_distribution_search")
    private boolean analyticDistributionSearch;

    @JsonProperty("analytic_line_ids")
    private List<Integer> analyticLineIds;

    @JsonProperty("analytic_precision")
    private int analyticPrecision;

    @JsonProperty("company_id")
    private int companyId;

    @JsonProperty("coupon_id")
    private boolean couponId;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("create_uid")
    private int createUid;

    @JsonProperty("currency_id")
    private int currencyId;

    @JsonProperty("customer_lead")
    private double customerLead;

    @JsonProperty("discount")
    private double discount;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("display_qty_widget")
    private boolean displayQtyWidget;

    @JsonProperty("display_type")
    private boolean displayType;

    @JsonProperty("forecast_expected_date")
    private boolean forecastExpectedDate;

    @JsonProperty("free_qty_today")
    private double freeQtyToday;

    @JsonProperty("invoice_lines")
    private List<Integer> invoiceLines;

    @JsonProperty("invoice_status")
    private String invoiceStatus;

    @JsonProperty("is_configurable_product")
    private boolean isConfigurableProduct;

    @JsonProperty("is_delivery")
    private boolean isDelivery;

    @JsonProperty("is_downpayment")
    private boolean isDownpayment;

    @JsonProperty("is_expense")
    private boolean isExpense;

    @JsonProperty("is_mto")
    private boolean isMto;

    @JsonProperty("is_reward_line")
    private boolean isRewardLine;

    @JsonProperty("is_service")
    private boolean isService;

    @JsonProperty("linked_line_id")
    private boolean linkedLineId;

    @JsonProperty("move_ids")
    private List<Integer> moveIds;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_short")
    private String nameShort;

    @JsonProperty("option_line_ids")
    private List<Integer> optionLineIds;

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("order_partner_id")
    private int orderPartnerId;

    @JsonProperty("points_cost")
    private double pointsCost;

    @JsonProperty("price_reduce_taxexcl")
    private double priceReduceTaxExcl;

    @JsonProperty("price_reduce_taxinc")
    private double priceReduceTaxInc;

    @JsonProperty("price_subtotal")
    private double priceSubtotal;

    @JsonProperty("price_tax")
    private double priceTax;

    @JsonProperty("price_total")
    private double priceTotal;

    @JsonProperty("price_unit")
    private double priceUnit;

    @JsonProperty("pricelist_item_id")
    private boolean pricelistItemId;

    @JsonProperty("product_custom_attribute_value_ids")
    private List<Integer> productCustomAttributeValueIds;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("product_no_variant_attribute_value_ids")
    private List<Integer> productNoVariantAttributeValueIds;

    @JsonProperty("product_packaging_id")
    private boolean productPackagingId;

    @JsonProperty("product_packaging_qty")
    private double productPackagingQty;

    @JsonProperty("product_qty")
    private double productQty;

    @JsonProperty("product_template_attribute_value_ids")
    private List<Integer> productTemplateAttributeValueIds;

    @JsonProperty("product_template_id")
    private int productTemplateId;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("product_uom")
    private int productUom;

    @JsonProperty("product_uom_category_id")
    private int productUomCategoryId;

    @JsonProperty("product_uom_qty")
    private double productUomQty;

    @JsonProperty("product_uom_readonly")
    private boolean productUomReadonly;

    @JsonProperty("product_updatable")
    private boolean productUpdatable;

    @JsonProperty("project_id")
    private boolean projectId;

    @JsonProperty("purchase_line_count")
    private int purchaseLineCount;

    @JsonProperty("purchase_line_ids")
    private List<Integer> purchaseLineIds;

    @JsonProperty("qty_available_today")
    private double qtyAvailableToday;

    @JsonProperty("qty_delivered")
    private double qtyDelivered;

    @JsonProperty("qty_delivered_method")
    private String qtyDeliveredMethod;

    @JsonProperty("qty_invoiced")
    private double qtyInvoiced;

    @JsonProperty("qty_to_deliver")
    private double qtyToDeliver;

    @JsonProperty("qty_to_invoice")
    private double qtyToInvoice;

    @JsonProperty("reached_milestones_ids")
    private List<Integer> reachedMilestonesIds;

    @JsonProperty("recompute_delivery_price")
    private boolean recomputeDeliveryPrice;

    @JsonProperty("reward_id")
    private boolean rewardId;

    @JsonProperty("reward_identifier_code")
    private boolean rewardIdentifierCode;

    @JsonProperty("route_id")
    private boolean routeId;

    @JsonProperty("sale_order_option_ids")
    private List<Integer> saleOrderOptionIds;

    @JsonProperty("salesman_id")
    private boolean salesmanId;

    @JsonProperty("scheduled_date")
    private boolean scheduledDate;

    @JsonProperty("sequence")
    private int sequence;

    @JsonProperty("shop_warning")
    private boolean shopWarning;

    @JsonProperty("state")
    private String state;

    @JsonProperty("task_id")
    private boolean taskId;

    @JsonProperty("tax_calculation_rounding_method")
    private String taxCalculationRoundingMethod;

    @JsonProperty("tax_country_id")
    private int taxCountryId;

    @JsonProperty("tax_id")
    private List<Integer> taxId;

    @JsonProperty("untaxed_amount_invoiced")
    private double untaxedAmountInvoiced;

    @JsonProperty("untaxed_amount_to_invoice")
    private double untaxedAmountToInvoice;

    @JsonProperty("virtual_available_at_date")
    private double virtualAvailableAtDate;

    @JsonProperty("warehouse_id")
    private int warehouseId;

    @JsonProperty("write_date")
    private String writeDate;

    @JsonProperty("write_uid")
    private int writeUid;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isAnalyticDistribution() {
        return analyticDistribution;
    }

    public void setAnalyticDistribution(boolean analyticDistribution) {
        this.analyticDistribution = analyticDistribution;
    }

    public boolean isAnalyticDistributionSearch() {
        return analyticDistributionSearch;
    }

    public void setAnalyticDistributionSearch(boolean analyticDistributionSearch) {
        this.analyticDistributionSearch = analyticDistributionSearch;
    }

    public List<Integer> getAnalyticLineIds() {
        return analyticLineIds;
    }

    public void setAnalyticLineIds(List<Integer> analyticLineIds) {
        this.analyticLineIds = analyticLineIds;
    }

    public int getAnalyticPrecision() {
        return analyticPrecision;
    }

    public void setAnalyticPrecision(int analyticPrecision) {
        this.analyticPrecision = analyticPrecision;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public boolean isCouponId() {
        return couponId;
    }

    public void setCouponId(boolean couponId) {
        this.couponId = couponId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getCreateUid() {
        return createUid;
    }

    public void setCreateUid(int createUid) {
        this.createUid = createUid;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getCustomerLead() {
        return customerLead;
    }

    public void setCustomerLead(double customerLead) {
        this.customerLead = customerLead;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isDisplayQtyWidget() {
        return displayQtyWidget;
    }

    public void setDisplayQtyWidget(boolean displayQtyWidget) {
        this.displayQtyWidget = displayQtyWidget;
    }

    public boolean isDisplayType() {
        return displayType;
    }

    public void setDisplayType(boolean displayType) {
        this.displayType = displayType;
    }

    public boolean isForecastExpectedDate() {
        return forecastExpectedDate;
    }

    public void setForecastExpectedDate(boolean forecastExpectedDate) {
        this.forecastExpectedDate = forecastExpectedDate;
    }

    public double getFreeQtyToday() {
        return freeQtyToday;
    }

    public void setFreeQtyToday(double freeQtyToday) {
        this.freeQtyToday = freeQtyToday;
    }

    public List<Integer> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<Integer> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public boolean isConfigurableProduct() {
        return isConfigurableProduct;
    }

    public void setConfigurableProduct(boolean configurableProduct) {
        isConfigurableProduct = configurableProduct;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public boolean isDownpayment() {
        return isDownpayment;
    }

    public void setDownpayment(boolean downpayment) {
        isDownpayment = downpayment;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public boolean isMto() {
        return isMto;
    }

    public void setMto(boolean mto) {
        isMto = mto;
    }

    public boolean isRewardLine() {
        return isRewardLine;
    }

    public void setRewardLine(boolean rewardLine) {
        isRewardLine = rewardLine;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public boolean isLinkedLineId() {
        return linkedLineId;
    }

    public void setLinkedLineId(boolean linkedLineId) {
        this.linkedLineId = linkedLineId;
    }

    public List<Integer> getMoveIds() {
        return moveIds;
    }

    public void setMoveIds(List<Integer> moveIds) {
        this.moveIds = moveIds;
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

    public List<Integer> getOptionLineIds() {
        return optionLineIds;
    }

    public void setOptionLineIds(List<Integer> optionLineIds) {
        this.optionLineIds = optionLineIds;
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

    public double getPointsCost() {
        return pointsCost;
    }

    public void setPointsCost(double pointsCost) {
        this.pointsCost = pointsCost;
    }

    public double getPriceReduceTaxExcl() {
        return priceReduceTaxExcl;
    }

    public void setPriceReduceTaxExcl(double priceReduceTaxExcl) {
        this.priceReduceTaxExcl = priceReduceTaxExcl;
    }

    public double getPriceReduceTaxInc() {
        return priceReduceTaxInc;
    }

    public void setPriceReduceTaxInc(double priceReduceTaxInc) {
        this.priceReduceTaxInc = priceReduceTaxInc;
    }

    public double getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(double priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }

    public double getPriceTax() {
        return priceTax;
    }

    public void setPriceTax(double priceTax) {
        this.priceTax = priceTax;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public boolean isPricelistItemId() {
        return pricelistItemId;
    }

    public void setPricelistItemId(boolean pricelistItemId) {
        this.pricelistItemId = pricelistItemId;
    }

    public List<Integer> getProductCustomAttributeValueIds() {
        return productCustomAttributeValueIds;
    }

    public void setProductCustomAttributeValueIds(List<Integer> productCustomAttributeValueIds) {
        this.productCustomAttributeValueIds = productCustomAttributeValueIds;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<Integer> getProductNoVariantAttributeValueIds() {
        return productNoVariantAttributeValueIds;
    }

    public void setProductNoVariantAttributeValueIds(List<Integer> productNoVariantAttributeValueIds) {
        this.productNoVariantAttributeValueIds = productNoVariantAttributeValueIds;
    }

    public boolean isProductPackagingId() {
        return productPackagingId;
    }

    public void setProductPackagingId(boolean productPackagingId) {
        this.productPackagingId = productPackagingId;
    }

    public double getProductPackagingQty() {
        return productPackagingQty;
    }

    public void setProductPackagingQty(double productPackagingQty) {
        this.productPackagingQty = productPackagingQty;
    }

    public double getProductQty() {
        return productQty;
    }

    public void setProductQty(double productQty) {
        this.productQty = productQty;
    }

    public List<Integer> getProductTemplateAttributeValueIds() {
        return productTemplateAttributeValueIds;
    }

    public void setProductTemplateAttributeValueIds(List<Integer> productTemplateAttributeValueIds) {
        this.productTemplateAttributeValueIds = productTemplateAttributeValueIds;
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

    public int getProductUom() {
        return productUom;
    }

    public void setProductUom(int productUom) {
        this.productUom = productUom;
    }

    public int getProductUomCategoryId() {
        return productUomCategoryId;
    }

    public void setProductUomCategoryId(int productUomCategoryId) {
        this.productUomCategoryId = productUomCategoryId;
    }

    public double getProductUomQty() {
        return productUomQty;
    }

    public void setProductUomQty(double productUomQty) {
        this.productUomQty = productUomQty;
    }

    public boolean isProductUomReadonly() {
        return productUomReadonly;
    }

    public void setProductUomReadonly(boolean productUomReadonly) {
        this.productUomReadonly = productUomReadonly;
    }

    public boolean isProductUpdatable() {
        return productUpdatable;
    }

    public void setProductUpdatable(boolean productUpdatable) {
        this.productUpdatable = productUpdatable;
    }

    public boolean isProjectId() {
        return projectId;
    }

    public void setProjectId(boolean projectId) {
        this.projectId = projectId;
    }

    public int getPurchaseLineCount() {
        return purchaseLineCount;
    }

    public void setPurchaseLineCount(int purchaseLineCount) {
        this.purchaseLineCount = purchaseLineCount;
    }

    public List<Integer> getPurchaseLineIds() {
        return purchaseLineIds;
    }

    public void setPurchaseLineIds(List<Integer> purchaseLineIds) {
        this.purchaseLineIds = purchaseLineIds;
    }

    public double getQtyAvailableToday() {
        return qtyAvailableToday;
    }

    public void setQtyAvailableToday(double qtyAvailableToday) {
        this.qtyAvailableToday = qtyAvailableToday;
    }

    public double getQtyDelivered() {
        return qtyDelivered;
    }

    public void setQtyDelivered(double qtyDelivered) {
        this.qtyDelivered = qtyDelivered;
    }

    public String getQtyDeliveredMethod() {
        return qtyDeliveredMethod;
    }

    public void setQtyDeliveredMethod(String qtyDeliveredMethod) {
        this.qtyDeliveredMethod = qtyDeliveredMethod;
    }

    public double getQtyInvoiced() {
        return qtyInvoiced;
    }

    public void setQtyInvoiced(double qtyInvoiced) {
        this.qtyInvoiced = qtyInvoiced;
    }

    public double getQtyToDeliver() {
        return qtyToDeliver;
    }

    public void setQtyToDeliver(double qtyToDeliver) {
        this.qtyToDeliver = qtyToDeliver;
    }

    public double getQtyToInvoice() {
        return qtyToInvoice;
    }

    public void setQtyToInvoice(double qtyToInvoice) {
        this.qtyToInvoice = qtyToInvoice;
    }

    public List<Integer> getReachedMilestonesIds() {
        return reachedMilestonesIds;
    }

    public void setReachedMilestonesIds(List<Integer> reachedMilestonesIds) {
        this.reachedMilestonesIds = reachedMilestonesIds;
    }

    public boolean isRecomputeDeliveryPrice() {
        return recomputeDeliveryPrice;
    }

    public void setRecomputeDeliveryPrice(boolean recomputeDeliveryPrice) {
        this.recomputeDeliveryPrice = recomputeDeliveryPrice;
    }

    public boolean isRewardId() {
        return rewardId;
    }

    public void setRewardId(boolean rewardId) {
        this.rewardId = rewardId;
    }

    public boolean isRewardIdentifierCode() {
        return rewardIdentifierCode;
    }

    public void setRewardIdentifierCode(boolean rewardIdentifierCode) {
        this.rewardIdentifierCode = rewardIdentifierCode;
    }

    public boolean isRouteId() {
        return routeId;
    }

    public void setRouteId(boolean routeId) {
        this.routeId = routeId;
    }

    public List<Integer> getSaleOrderOptionIds() {
        return saleOrderOptionIds;
    }

    public void setSaleOrderOptionIds(List<Integer> saleOrderOptionIds) {
        this.saleOrderOptionIds = saleOrderOptionIds;
    }

    public boolean isSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(boolean salesmanId) {
        this.salesmanId = salesmanId;
    }

    public boolean isScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(boolean scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isShopWarning() {
        return shopWarning;
    }

    public void setShopWarning(boolean shopWarning) {
        this.shopWarning = shopWarning;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isTaskId() {
        return taskId;
    }

    public void setTaskId(boolean taskId) {
        this.taskId = taskId;
    }

    public String getTaxCalculationRoundingMethod() {
        return taxCalculationRoundingMethod;
    }

    public void setTaxCalculationRoundingMethod(String taxCalculationRoundingMethod) {
        this.taxCalculationRoundingMethod = taxCalculationRoundingMethod;
    }

    public int getTaxCountryId() {
        return taxCountryId;
    }

    public void setTaxCountryId(int taxCountryId) {
        this.taxCountryId = taxCountryId;
    }

    public List<Integer> getTaxId() {
        return taxId;
    }

    public void setTaxId(List<Integer> taxId) {
        this.taxId = taxId;
    }

    public double getUntaxedAmountInvoiced() {
        return untaxedAmountInvoiced;
    }

    public void setUntaxedAmountInvoiced(double untaxedAmountInvoiced) {
        this.untaxedAmountInvoiced = untaxedAmountInvoiced;
    }

    public double getUntaxedAmountToInvoice() {
        return untaxedAmountToInvoice;
    }

    public void setUntaxedAmountToInvoice(double untaxedAmountToInvoice) {
        this.untaxedAmountToInvoice = untaxedAmountToInvoice;
    }

    public double getVirtualAvailableAtDate() {
        return virtualAvailableAtDate;
    }

    public void setVirtualAvailableAtDate(double virtualAvailableAtDate) {
        this.virtualAvailableAtDate = virtualAvailableAtDate;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public int getWriteUid() {
        return writeUid;
    }

    public void setWriteUid(int writeUid) {
        this.writeUid = writeUid;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
