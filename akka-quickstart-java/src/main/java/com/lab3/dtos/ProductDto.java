package com.lab3.dtos;

import com.lab3.refrigerator.Product;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class ProductDto {

    private String name = "";
    private double prize = 0;
    private double weightInGramm = 0;

    public ProductDto(){}

    public ProductDto(Product pro){
        if(pro != null){
            this.name = pro.getName();
            this.prize = pro.getPrize();
            this.weightInGramm = pro.getWeightInGramm();
        }

    }

    @Schema
    public String getName() {
        return name;
    }

    @Schema
    public double getWeightInGramm() {
        return weightInGramm;
    }


    @Schema
    public double getPrize() {
        return prize;
    }


    @Override
    public String toString() {
        return "Product" +
                "name ='" + name + '\'' +
                ", prize =" + prize +
                ", weightInGramm =" + weightInGramm;
    }
}
