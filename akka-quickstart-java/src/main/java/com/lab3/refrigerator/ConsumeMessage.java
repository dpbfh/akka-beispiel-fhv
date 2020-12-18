package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;

@Introspected

public class ConsumeMessage implements IFridgeMessage {

    private Product product;
    private boolean anwser = false;

    public ConsumeMessage() {
        super();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void setAnwser(boolean x) {
        anwser = x;
    }

    @Override
    public boolean isAnwser() {
        return anwser;
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
