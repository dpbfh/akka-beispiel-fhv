package com.lab3.dtos;

import com.lab3.refrigerator.Product;
import io.micronaut.core.annotation.Introspected;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Introspected
public class StoredProductsMessageDto  {

    private boolean answer = false;
    private List<ProductDto> inventory = new ArrayList<>();

    public void setAnwser(boolean x) {
        answer = x;
    }

    public boolean isAnwser() {
        return answer;
    }


    public List<ProductDto> getInventory() {
        return inventory;
    }

    public void setInventory(List<Product> inventory) {
        if(inventory!= null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Product next : inventory) {
                this.inventory.add(new ProductDto(next));
            }
        }else
        {
            this.inventory = Collections.EMPTY_LIST;
        }
    }

    private String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String msg) {
        errorMsg = msg;
    }
}
