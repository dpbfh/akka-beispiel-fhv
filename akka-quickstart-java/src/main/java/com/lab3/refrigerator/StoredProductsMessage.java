package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;

import java.util.List;
@Introspected

public class StoredProductsMessage implements IFridgeMessage {

    private boolean answer = false;
    private List<Product> inventory;

    @Override
    public void setAnwser(boolean x) {
        answer = x;
    }

    @Override
    public boolean isAnwser() {
        return answer;
    }


    public List<Product> getInventory() {
            return inventory;
    }

    public void setInventory(List<Product> inventory) {
            this.inventory = inventory;

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
