package org.simexid.odoo.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PojoReflectionBulkTest {

    @Test
    void exerciseMultiplePojos() throws Exception {
        Class<?>[] classes = new Class<?>[]{Customer.class, CustomerHook.class, SaleOrder.class, SaleOrderLine.class};
        for (Class<?> cls : classes) {
            Object obj = cls.getDeclaredConstructor().newInstance();
            Method[] methods = cls.getMethods();
            for (Method m : methods) {
                String name = m.getName();
                if (name.startsWith("set") && m.getParameterCount() == 1) {
                    Class<?> p = m.getParameterTypes()[0];
                    Object sample = sampleValueFor(p);
                    try {
                        m.invoke(obj, sample);
                    } catch (IllegalArgumentException ia) {
                        if (p == int.class) m.invoke(obj, ((Number) sample).intValue());
                        else if (p == double.class) m.invoke(obj, ((Number) sample).doubleValue());
                        else m.invoke(obj, sample);
                    }
                    String prop = name.substring(3);
                    Method getter = null;
                    try {
                        getter = cls.getMethod("get" + prop);
                    } catch (NoSuchMethodException e) {
                        try {
                            getter = cls.getMethod("is" + prop);
                        } catch (NoSuchMethodException ex) {
                            // no getter available, skip
                        }
                    }
                    if (getter != null) {
                        Object val = getter.invoke(obj);
                        assertNotNull(val, () -> cls.getSimpleName() + ": property " + prop + " should not be null");
                    }
                }
            }
            // toString check
            String s = obj.toString();
            assertNotNull(s);
        }
    }

    private Object sampleValueFor(Class<?> p) {
        if (p == String.class) return "x";
        if (p == int.class || p == Integer.class) return 1;
        if (p == double.class || p == Double.class) return 1.2;
        if (p == boolean.class || p == Boolean.class) return true;
        if (List.class.isAssignableFrom(p)) return List.of(1);
        // handle common nested POJO types used in models
        try {
            if (SaleOrder.Partner.class.isAssignableFrom(p)) {
                return new SaleOrder.Partner(1, "p");
            }
            if (SaleOrderLine.Product.class.isAssignableFrom(p)) {
                return new SaleOrderLine.Product(1, "p");
            }
            // try default constructor
            try {
                return p.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException ns) {
                // try constructor (int, String)
                try {
                    return p.getDeclaredConstructor(int.class, String.class).newInstance(1, "x");
                } catch (NoSuchMethodException ex) {
                    // fallback
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
}
