package com.lab3.dtos;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OrderMessageDto  {

    private int amount;
    private ProductDto product;
    private boolean anwser = false;

    public OrderMessageDto() {
        super();
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private boolean answer = false;

    public void setAnwser(boolean x) {
        answer = x;
    }

    public boolean isAnwser() {
        return answer;
    }

    private String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String msg) {
        errorMsg = msg;
    }
}
