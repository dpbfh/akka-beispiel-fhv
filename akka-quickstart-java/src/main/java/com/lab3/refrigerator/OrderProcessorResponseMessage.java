package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected

public class OrderProcessorResponseMessage implements IFridgeMessage {
    private boolean anwser = false;
    private String errorMsg = "";
    private Receipt receipt;
    private List<Product> productList;
    @Override
    public void setAnwser(boolean x) {anwser = x;
    }

    @Override
    public boolean isAnwser() {
        return anwser;
    }




    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String msg) {
        errorMsg = msg;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
