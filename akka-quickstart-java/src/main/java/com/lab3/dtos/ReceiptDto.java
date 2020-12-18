package com.lab3.dtos;

import com.lab3.refrigerator.Product;
import com.lab3.refrigerator.Receipt;
import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Introspected
public class ReceiptDto {

    private long timeOfOrder;
    private ProductDto product;
    private int amount;
    private double totalPrize;

    public ReceiptDto(Receipt receipt){
        this.amount = receipt.getAmount();
        this.timeOfOrder = receipt.getTimeOfOrder().atZone(ZoneId.systemDefault()).toEpochSecond();
        this.product = new ProductDto(receipt.getProduct());
        this.totalPrize = receipt.getTotalPrize();
    }


    public long getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(LocalDateTime timeOfOrder) {
        this.timeOfOrder = timeOfOrder.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = new ProductDto(product);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(double totalPrize) {
        this.totalPrize = totalPrize;
    }

    @Override
    public String toString() {
        return "Receipt" +
                "timeOfOrder =" + timeOfOrder +
                ", product =" + product +
                ", amount =" + amount +
                ", totalPrize =" + totalPrize;
    }
}
