package com.lab3.dtos.Assembler;

import com.lab3.dtos.ConsumeMessageDto;
import com.lab3.refrigerator.ConsumeMessage;
import com.lab3.refrigerator.Product;

public class ConsumerMessageAssembler {
    public static ConsumeMessageDto createDto(ConsumeMessage msg){
        var msgdto = new ConsumeMessageDto();
        msgdto.setAnwser(msg.isAnwser());
        msgdto.setProduct(msg.getProduct());
        msgdto.setErrorMsg(msg.getErrorMsg());
        return msgdto;
    }

    public static ConsumeMessage getConsumeMessage(ConsumeMessageDto msgdto){
        var msg = new ConsumeMessage();
        msg.setAnwser(msgdto.isAnwser());
        msg.setProduct(Product.getByName(msgdto.getProduct().getName()));
        msg.setErrorMsg(msgdto.getErrorMsg());
        return msg;
    }
}
