package com.lab3.dtos.Assembler;

import com.lab3.dtos.ConsumeMessageDto;
import com.lab3.dtos.OrderMessageDto;
import com.lab3.dtos.ProductDto;
import com.lab3.refrigerator.ConsumeMessage;
import com.lab3.refrigerator.OrderMessage;
import com.lab3.refrigerator.Product;

public class OrderMessageAssembler {
    public static OrderMessageDto createDto(OrderMessage msg){
        var msgdto = new OrderMessageDto();
        msgdto.setAnwser(msg.isAnwser());
        msgdto.setProduct(new ProductDto(msg.getProduct()));
        msgdto.setAmount(msg.getAmount());
        msgdto.setErrorMsg(msg.getErrorMsg());
        return msgdto;
    }

    public static OrderMessage getOrderMessage(OrderMessageDto msgdto){
        var msg = new OrderMessage();
        msg.setAnwser(msgdto.isAnwser());
        msg.setProduct(Product.getByName(msgdto.getProduct().getName()));
        msg.setErrorMsg(msgdto.getErrorMsg());
        msg.setAmount(msgdto.getAmount());
        return msg;
    }
}
