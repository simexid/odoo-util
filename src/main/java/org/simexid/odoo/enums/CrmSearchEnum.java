package org.simexid.odoo.enums;

/**
 * Enum class for Odoo CRM search operations
 */
public class CrmSearchEnum {

    /**
     * Search fields for querying Odoo CRM
     */
    public enum SearchField {
        name("name"),
        email("email"),
        phone("phone"),
        partnerid("id"),
        partnerinvoiceid("partner_invoice_id"),
        partnershippingid("partner_shipping_id"),
        orderid("id"),
        id("id");


        private final String fieldName;

        SearchField(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }

    /**
     * Search operators for querying Odoo CRM
     */
    public enum SearchOperator {
        equal("="),
        like("ilike"),
        not_equal("!="),
        greater_than(">"),
        less_than("<");

        private final String operator;

        SearchOperator(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }

    /**
     * Odoo crm available models
     */
    public enum Model {
        partner("res.partner"),
        saleorder("sale.order"),
        saleorderline("sale.order.line"),
        accountmove("account.move"),
        accountpayment("account.payment"),
        accountjournal("account.journal"),
        accounttax("account.tax"),
        purchaseorder("purchase.order"),
        purchaseorderline("purchase.order.line"),
        projecttask("project.task"),
        project("project.project"),
        paymenttranasction("payment.transaction"),
        crmlead("crm.lead"),
        salereport("sale.report");

        private final String model;

        Model(String model) {
            this.model = model;
        }

        public String getModel() {
            return model;
        }
    }

    /**
     * Odoo Sale order states
     */
    public enum State {
        draft("draft"),
        sent("sent"),
        sale("sale"),
        done("done"),
        cancel("cancel");

        private final String state;

        State(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }
}
