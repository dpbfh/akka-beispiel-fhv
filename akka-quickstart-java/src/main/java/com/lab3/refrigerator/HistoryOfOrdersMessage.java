package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;

import java.util.LinkedList;
import java.util.List;
@Introspected

public class HistoryOfOrdersMessage implements IFridgeMessage{

    private boolean answer = false;

    @Override
    public void setAnwser(boolean x) {
        answer = x;
    }

    @Override
    public boolean isAnwser() {
        return answer;
    }

    private List<Receipt> receiptList = new LinkedList<>();

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }

    @Override
    public void setErrorMsg(String msg) {

    }
}
