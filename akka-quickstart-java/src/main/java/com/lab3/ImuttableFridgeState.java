package com.lab3;

import com.lab3.dtos.Assembler.ConsumerMessageAssembler;
import com.lab3.dtos.Assembler.HistoryOrderMessageAssembler;
import com.lab3.dtos.Assembler.OrderMessageAssembler;
import com.lab3.dtos.Assembler.StoredProductsMessageAssembler;
import com.lab3.dtos.ConsumeMessageDto;
import com.lab3.dtos.HistoryOfOrdersMessageDto;
import com.lab3.dtos.OrderMessageDto;
import com.lab3.dtos.StoredProductsMessageDto;
import com.lab3.refrigerator.ConsumeMessage;
import com.lab3.refrigerator.HistoryOfOrdersMessage;
import com.lab3.refrigerator.OrderMessage;
import com.lab3.refrigerator.StoredProductsMessage;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class ImuttableFridgeState {
    private  OrderMessageDto lastOrderMessage;
    private  ConsumeMessageDto lastConsumeMessage;
    private  HistoryOfOrdersMessageDto historyOfOrdersMessage;
    private  StoredProductsMessageDto storedProductsMessage;

    public  ImuttableFridgeState(FridgeState state){
        if(state.getLastOrderMessage() != null){
            lastOrderMessage = OrderMessageAssembler.createDto(state.getLastOrderMessage());
        }

        if(state.getLastConsumeMessage() != null){
            lastConsumeMessage = ConsumerMessageAssembler.createDto(state.getLastConsumeMessage());
        }

        if(state.getHistoryOfOrders() != null){
            historyOfOrdersMessage = HistoryOrderMessageAssembler.createDto(state.getHistoryOfOrders());
        }

        if(state.getStoredProductsMessage() != null) {
            storedProductsMessage = StoredProductsMessageAssembler.createDto(state.getStoredProductsMessage());
        }
    }

    public OrderMessageDto getLastOrderMessage() {
        return lastOrderMessage;
    }

    public ConsumeMessageDto getLastConsumeMessage() {
        return lastConsumeMessage;
    }

    public HistoryOfOrdersMessageDto getHistoryOfOrdersMessage() {
        return historyOfOrdersMessage;
    }

    public StoredProductsMessageDto getStoredProductsMessage() {
        return storedProductsMessage;
    }
}
