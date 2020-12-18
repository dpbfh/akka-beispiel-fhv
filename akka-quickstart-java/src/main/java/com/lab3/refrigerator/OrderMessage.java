package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;

@Introspected

public class OrderMessage implements IFridgeMessage {

    private int amount;
    private Product product;
    private boolean isOrderProcessor = false;
    private boolean answer = false;

    public OrderMessage() {
        super();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public void setAnwser(boolean x) {
        answer = x;
    }

    @Override
    public boolean isAnwser() {
        return answer;
    }

    public boolean isOrderProcessorResponse() {
        return isOrderProcessor;
    }

    public void setIsOrderProcessor(boolean orderProcessor) {
        isOrderProcessor = orderProcessor;
    }
    private String errorMsg = "";

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String msg) {
        errorMsg = msg;
    }
}
