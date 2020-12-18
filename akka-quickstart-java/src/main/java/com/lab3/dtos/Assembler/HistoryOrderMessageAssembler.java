package com.lab3.dtos.Assembler;

import com.lab3.dtos.HistoryOfOrdersMessageDto;
import com.lab3.dtos.OrderMessageDto;
import com.lab3.dtos.ProductDto;
import com.lab3.dtos.ReceiptDto;
import com.lab3.refrigerator.HistoryOfOrdersMessage;
import com.lab3.refrigerator.OrderMessage;
import com.lab3.refrigerator.Product;
import com.lab3.refrigerator.Receipt;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

public class HistoryOrderMessageAssembler {
    public static HistoryOfOrdersMessageDto createDto(HistoryOfOrdersMessage msg){
        var msgdto = new HistoryOfOrdersMessageDto();
        msgdto.setAnwser(msg.isAnwser());
        msgdto.setReceiptList(msg.getReceiptList().stream().map(ReceiptDto::new).collect(Collectors.toList()));
        msgdto.setErrorMsg(msg.getErrorMsg());
        return msgdto;
    }

    public static HistoryOfOrdersMessage getOrderMessage(HistoryOfOrdersMessageDto msgdto){
        var msg = new HistoryOfOrdersMessage();
        msg.setAnwser(msgdto.isAnwser());
        OffsetDateTime odt = OffsetDateTime.now ();
        ZoneOffset zoneOffset = odt.getOffset ();
        msg.setReceiptList(msgdto.getReceiptList().stream().map(x -> new Receipt(
                LocalDateTime.ofEpochSecond(x.getTimeOfOrder(),0,zoneOffset),
                    Product.getByName(x.getProduct().getName()),
                x.getAmount(),
                x.getTotalPrize()
                )).collect(Collectors.toList()));
        msg.setErrorMsg(msgdto.getErrorMsg());
        return msg;
    }
}
