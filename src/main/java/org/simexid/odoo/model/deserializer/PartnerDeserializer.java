package org.simexid.odoo.model.deserializer;

import com.google.gson.*;
import org.simexid.odoo.model.SaleOrder;

import java.lang.reflect.Type;

/**
 * Deserializer for Partner object in SaleOrder
 */
public class PartnerDeserializer implements JsonDeserializer<SaleOrder.Partner> {
    @Override
    public SaleOrder.Partner deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonPrimitive) {
            try {
                int value = json.getAsInt();
                return new SaleOrder.Partner(value, null);
            } catch (NumberFormatException e) {
                return new SaleOrder.Partner(0, json.getAsString());
            }
        } else {
            JsonArray jsonArray = json.getAsJsonArray();
            int id = jsonArray.get(0).getAsInt();
            String name = jsonArray.get(1).getAsString();
            return new SaleOrder.Partner(id, name);
        }
    }
}