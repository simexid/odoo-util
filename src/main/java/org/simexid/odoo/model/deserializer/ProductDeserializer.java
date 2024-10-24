package org.simexid.odoo.model.deserializer;

import com.google.gson.*;
import org.simexid.odoo.model.SaleOrderLine;

import java.lang.reflect.Type;

/**
 * Deserializer class for Product
 */
public class ProductDeserializer implements JsonDeserializer<SaleOrderLine.Product > {
    @Override
    public SaleOrderLine.Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonPrimitive) {
            try {
                int value = json.getAsInt();
                return new SaleOrderLine.Product(value, null);
            } catch (NumberFormatException e) {
                return new SaleOrderLine.Product(0, json.getAsString());
            }
        } else {
            JsonArray jsonArray = json.getAsJsonArray();
            int id = jsonArray.get(0).getAsInt();
            String name = jsonArray.get(1).getAsString();
            return new SaleOrderLine.Product(id, name);
        }
    }
}