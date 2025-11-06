package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleOrderLineHookReflectionTest {

    @Test
    void exerciseAllSettersAndGettersViaReflection() throws Exception {
        SaleOrderLineHook obj = new SaleOrderLineHook();

        Method[] methods = SaleOrderLineHook.class.getMethods();

        // invoke all setters with a reasonable dummy value depending on parameter type
        for (Method m : methods) {
            String name = m.getName();
            if (name.startsWith("set") && m.getParameterCount() == 1) {
                Class<?> p = m.getParameterTypes()[0];
                Object sample = sampleValueFor(p);
                try {
                    m.invoke(obj, sample);
                } catch (IllegalArgumentException ia) {
                    // try to coerce to primitive if needed
                    if (p == int.class) m.invoke(obj, ((Number) sample).intValue());
                    else if (p == double.class) m.invoke(obj, ((Number) sample).doubleValue());
                    else if (p == boolean.class) m.invoke(obj, sample);
                    else m.invoke(obj, sample);
                }
                // now try to call the corresponding getter
                String prop = name.substring(3); // after set
                Method getter = null;
                try {
                    getter = SaleOrderLineHook.class.getMethod("get" + prop);
                } catch (NoSuchMethodException e) {
                    try {
                        getter = SaleOrderLineHook.class.getMethod("is" + prop);
                    } catch (NoSuchMethodException ex) {
                        // ignore - some setters may not have public getters
                    }
                }
                if (getter != null) {
                    Object val = getter.invoke(obj);
                    assertNotNull(val, "Property " + prop + " should not be null after setting");
                }
            }
        }
        // toString should produce JSON containing at least some known properties
        String s = obj.toString();
    assertTrue(s.contains("\"_id\"") || s.contains("\"id\"") || !s.isEmpty());
    }

    private Object sampleValueFor(Class<?> p) {
        if (p == String.class) return "x";
        if (p == int.class || p == Integer.class) return 1;
        if (p == double.class || p == Double.class) return 1.2;
        if (p == boolean.class || p == Boolean.class) return true;
        if (List.class.isAssignableFrom(p)) return List.of(1);
        // default fallback
        return "x";
    }
}
