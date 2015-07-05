package com.pingbits;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Ordder {

    public static Entity addEntity(Schema schema) {
        Entity order = schema.addEntity("Ordder");
        order.addIdProperty();

        order.addStringProperty("name").notNull();
        order.addDateProperty("date").notNull();
        return order;
    }
}
