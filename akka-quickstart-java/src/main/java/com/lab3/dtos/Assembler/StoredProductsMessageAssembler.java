package com.lab3.dtos.Assembler;

import com.lab3.dtos.StoredProductsMessageDto;
import com.lab3.dtos.ReceiptDto;
import com.lab3.refrigerator.StoredProductsMessage;
import com.lab3.refrigerator.Product;
import com.lab3.refrigerator.Receipt;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

public class StoredProductsMessageAssembler {
    public static StoredProductsMessageDto createDto(StoredProductsMessage msg){
        var msgdto = new StoredProductsMessageDto();
        msgdto.setAnwser(msg.isAnwser());
        msgdto.setInventory(msg.getInventory());
        msgdto.setErrorMsg(msg.getErrorMsg());
        return msgdto;
    }

    public static StoredProductsMessage getOrderMessage(StoredProductsMessageDto msgdto){
        var msg = new StoredProductsMessage();
        msg.setAnwser(msgdto.isAnwser());
        msg.setInventory(msgdto.getInventory().stream().map(x -> Product.getByName(x.getName())).collect(Collectors.toList()));
        msg.setErrorMsg(msgdto.getErrorMsg());
        return msg;
    }
}
