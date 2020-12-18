package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;
@Introspected
public class Receipt {

    private LocalDateTime timeOfOrder;
    private Product product;
    private int amount;
    private double totalPrize;

    public Receipt(LocalDateTime timeOfOrder, Product product, int amount, double totalPrize){
        this.amount = amount;
        this.timeOfOrder = timeOfOrder;
        this.product = product;
        this.totalPrize = totalPrize;
    }


    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(LocalDateTime timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
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
