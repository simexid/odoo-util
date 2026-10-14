package org.simexid.odoo.model;

import java.util.List;

/**
 * Free-form read query against any Odoo model (see {@code POST /{api-path}/search}).
 *
 * @param model  Odoo model name, e.g. {@code res.partner}
 * @param domain Odoo search domain, e.g. {@code [["partner_id","in",[1,2]],["state","=","active"]]};
 *               null or empty means "all records"
 * @param fields fields to return; required, so that a caller never pulls whole records by accident
 * @param limit  maximum number of records (capped by {@code simexid.crm.search-max-limit})
 * @param offset records to skip
 * @param order  Odoo order clause, e.g. {@code "next_renewal_date asc"}
 */
public record SearchRequest(
        String model,
        List<Object> domain,
        List<String> fields,
        Integer limit,
        Integer offset,
        String order) {
}
