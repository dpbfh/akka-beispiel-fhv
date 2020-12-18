package com.lab3;

import com.lab3.refrigerator.ConsumeMessage;
import com.lab3.refrigerator.HistoryOfOrdersMessage;
import com.lab3.refrigerator.OrderMessage;
import com.lab3.refrigerator.StoredProductsMessage;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class FridgeState {
    private OrderMessage lastOrderMessage;
    private ConsumeMessage lastConsumeMessage;
    private HistoryOfOrdersMessage historyOfOrders;
    private StoredProductsMessage storedProductsMessage;

    public OrderMessage getLastOrderMessage() {
        return lastOrderMessage;
    }

    public void setLastOrderMessage(OrderMessage orderMessage) {
        this.lastOrderMessage = orderMessage;

    }

    public ConsumeMessage getLastConsumeMessage() {
        return lastConsumeMessage;
    }

    public void setLastConsumeMessage(ConsumeMessage lastConsumeMessage) {
        this.lastConsumeMessage = lastConsumeMessage;
    }

    public StoredProductsMessage getStoredProductsMessage() {
        return storedProductsMessage;
    }

    public void setStoredProductsMessage(StoredProductsMessage storedProductsMessage) {
        this.storedProductsMessage = storedProductsMessage;
    }

    public HistoryOfOrdersMessage getHistoryOfOrders() {
        return historyOfOrders;
    }

    public void setHistoryOfOrders(HistoryOfOrdersMessage historyOfOrders) {
        this.historyOfOrders = historyOfOrders;
    }
}
