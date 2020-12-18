package com.lab3.refrigerator;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum  Product {
    MILK("Milk",3.00d,1000d),
    BUTTER("Butter",4.00d,500d),
    ZUCCHINI("Zucchini ",1.00d,100d),
    TOMATO("Tomato",1.50d,250d),
    EGG("Egg",0.50d,50d),
    WHISKEY("Whiskey",30.00d,500d),
    VODKA("Vodka",30.00d,500d),
    GIN("Gin",30.00d,500d),
    BEER("Beer",3.00d,500d),
    POLAR_COD("Polar cod",8.25,1500d),
    TEQUILA_SILVER("Tequila Silver",45.75,525),
    FROHE("Frohe",24.12,20.20),
    WEIHNACHTEN("2020",24.12,20.20),
    TEQUILA_GOLD("Tequila Gold",40.75,775),
    JAEGER_MEISTER("Jaeger Meister",19.9,600),
    MOJITO("Mojito",8,250),
    CYDER("Cider",2.18,330),
    DEFAULT("DEFAULT",0,0),
    EGGNOG("Eggnog",14,530);

    private String name = "";
    private double prize = 0;
    private double weightInGramm = 0;

    Product(String name, double prize, double weightInGramm){
        this.name = name;
        this.prize = prize;
        this.weightInGramm = weightInGramm;
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

    public static Product getByName(String name){
        for(var product : Product.values()){
            if(product.getName().toLowerCase().equals(name.toLowerCase())){
               return product;
            }
        }
        return DEFAULT;
    }
}
