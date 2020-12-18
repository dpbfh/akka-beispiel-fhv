package com.lab3.dtos;

import com.lab3.refrigerator.Product;
import io.micronaut.core.annotation.Introspected;

@Introspected

public class ConsumeMessageDto {

    private ProductDto product;
    private boolean anwser = false;

    public ConsumeMessageDto() {
        super();
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = new ProductDto(product);
    }
    public void setProduct(ProductDto product) {
        this.product = product;
    }


    public void setAnwser(boolean x) {
        anwser = x;
    }

    public boolean isAnwser() {
        return anwser;
    }


    private String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String msg) {
        errorMsg = msg;
    }

}
