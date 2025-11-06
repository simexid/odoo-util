package org.simexid.odoo.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumCoverageTest {

    @Test
    void testSearchField() {
        for (CrmSearchEnum.SearchField f : CrmSearchEnum.SearchField.values()) {
            assertNotNull(f.getFieldName());
        }
    }

    @Test
    void testSearchOperator() {
        for (CrmSearchEnum.SearchOperator o : CrmSearchEnum.SearchOperator.values()) {
            assertNotNull(o.getOperator());
        }
    }

    @Test
    void testModelEnum() {
        for (CrmSearchEnum.Model m : CrmSearchEnum.Model.values()) {
            assertNotNull(m.getModel());
        }
    }
}
