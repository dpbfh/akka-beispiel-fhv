package com.lab3.dtos;

import io.micronaut.core.annotation.Introspected;

import java.util.LinkedList;
import java.util.List;

@Introspected

public class HistoryOfOrdersMessageDto {

    private boolean answer = false;

    public void setAnwser(boolean x) {
        answer = x;
    }

    public boolean isAnwser() {
        return answer;
    }

    private List<ReceiptDto> receiptList = new LinkedList<>();

    public List<ReceiptDto> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<ReceiptDto> receiptList) {
        this.receiptList = receiptList;
    }

    private String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String msg) {
        errorMsg = msg;
    }
}
