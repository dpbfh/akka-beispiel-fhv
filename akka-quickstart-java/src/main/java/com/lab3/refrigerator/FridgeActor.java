package com.lab3.refrigerator;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.lab3.IMessage;
import com.lab3.actors.ActorTyp;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class FridgeActor extends AbstractBehavior<IFridgeMessage> {

    private final int maxAmountOfProducts = 25;
    private final double maxWeightInGramm = 10000d;

    private final List<Product> inventory = fillFridge();
    private final HashMap<String, Integer> standardInventory = fillStandardInventory();
    private final List<Receipt> orderHistory = Collections.synchronizedList(new LinkedList<>());
    public final ActorRef<IMessage> replyTo;

    private FridgeActor(ActorContext<IFridgeMessage> context, ActorRef<IMessage> messageActorRef){
        super(context);
        replyTo = messageActorRef;
    }

    public static Behavior<IFridgeMessage> create(ActorRef <IMessage> messageActorRef) {
       return Behaviors.setup(context -> new FridgeActor(context, messageActorRef));
    }

    @Override
    public Receive<IFridgeMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(OrderMessage.class, this::order)
                .onMessage(ConsumeMessage.class, this::consume)
                .onMessage(HistoryOfOrdersMessage.class, this::historyOfOrders)
                .onMessage(StoredProductsMessage.class, this::storedProducts)
                .onMessage(OrderProcessorResponseMessage.class, this::orderResponse)
                .build();
    }

    private Behavior<IFridgeMessage> consume(ConsumeMessage consumeMessage){
        if(consumeMessage.isAnwser()){
            return Behaviors.same();
        }

        boolean isConsumed = false;
        synchronized(inventory){
            for (var product : inventory) {
                //Nur ein Produkt Konsumieren
                if(product.getName().equals(consumeMessage.getProduct().getName()))
                {
                    inventory.remove(product);
                    isConsumed = true;
                    break;
                }

            }
        }

        consumeMessage.setAnwser(true);
        if (!isConsumed) {
            System.out.println("***Error**"+"There is no "+ consumeMessage.getProduct().getName() + " in your Fridge, you cannot eat zomething what is not in there! Think more!");
            consumeMessage.setErrorMsg("There is no "+ consumeMessage.getProduct().getName() + " in your Fridge, you cannot eat zomething what is not in there! Think more!");
        }
        replyTo.tell(consumeMessage);

        if(inventory.stream().noneMatch(product -> product.getName().equals(consumeMessage.getProduct().getName()))){
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setProduct(consumeMessage.getProduct());
            orderMessage.setAmount(standardInventory.get(consumeMessage.getProduct().getName()));
            order(orderMessage);
        }
        return Behaviors.same();
    }

    private Behavior<IFridgeMessage> orderResponse(OrderProcessorResponseMessage msg){
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setAnwser(true);
            if(msg.getProductList() != null){
                orderMessage.setAmount(msg.getProductList().size());
                if(msg.getProductList().size()>0){
                    orderMessage.setProduct(msg.getProductList().get(0));
                }
            }

            orderMessage.setErrorMsg(msg.getErrorMsg());
            if(msg.getErrorMsg().equals("")){
               inventory.addAll(msg.getProductList());
               orderHistory.add(msg.getReceipt());
            }
            replyTo.tell(orderMessage);
            return Behaviors.same();

    }

    int childnumber = 0;
    private Behavior<IFridgeMessage> order(OrderMessage orderMessage) {
        if(orderMessage.isAnwser()){
            return Behaviors.same();
        }
        //Wieso sollten die Sensoren als Aktoren gebautwerden wenn der Kühlschrank alle informationen davon hällt und zuerst sagen müsste Sensor hier 10 Eier mit je 10 gramm,
        // diese Lösung fanden wir hier leichter
            if (checkFreeSpace(orderMessage.getAmount())) {
                if (!checkFreeWeight(orderMessage.getProduct().getWeightInGramm() * orderMessage.getAmount())) {
                    System.out.println("****Error: The weight limit has already reached already.");
                    orderMessage.setErrorMsg("The weight limit has already reached already.");
                }
            }else{
                System.out.println("****Error: There was no space in the Fridge.");
                orderMessage.setErrorMsg("There was no space in the Fridge.");
            }
        var actor = getContext().spawn(OrderProcessorActor.create(getContext().getSelf()), "Child"+(childnumber++));
        actor.tell(orderMessage);
        return Behaviors.same();
    }

    private Behavior<IFridgeMessage> historyOfOrders(HistoryOfOrdersMessage historyOfOrdersMessage){
        if(historyOfOrdersMessage.isAnwser()){
            return Behaviors.same();
        }

        System.out.println("*********Order History**********");
        for(Receipt receipt : orderHistory){
            System.out.println(receipt.toString());
        }
        System.out.println("*********Order History End**********");

        historyOfOrdersMessage.setAnwser(true);
        historyOfOrdersMessage.setReceiptList(Collections.unmodifiableList(orderHistory));
        replyTo.tell(historyOfOrdersMessage);
        return Behaviors.same();
    }

    private Behavior<IFridgeMessage> storedProducts(StoredProductsMessage storedProductsMessage){
        if(storedProductsMessage.isAnwser()){
            return Behaviors.same();
        }


        System.out.println("*********Fridge Inventory**********");
        for(Product product : inventory){
            System.out.println(product.toString());
        }
        System.out.println("***********Fridge Inventory END***********");

        storedProductsMessage.setAnwser(true);
        storedProductsMessage.setInventory(Collections.unmodifiableList(inventory));
        replyTo.tell(storedProductsMessage);
        return Behaviors.same();
    }


    private boolean checkFreeSpace(int amountOfOrderedProducts){
        int productsInFridge = amountOfOrderedProducts;

        productsInFridge += inventory.size();
        return (maxAmountOfProducts >= productsInFridge);
    }

    private boolean checkFreeWeight(double weightOfOrderedProductsInGramm){
        double weightInFridgeInGramm = weightOfOrderedProductsInGramm;
        for(Product product : inventory){
            weightInFridgeInGramm += product.getWeightInGramm();
        }
        return (maxWeightInGramm >= weightInFridgeInGramm);
    }

    private static HashMap<String, Integer> fillStandardInventory() {
        HashMap<String, Integer> standardInventory = new HashMap<>();
        for(Product product : fillFridge()){
            int temp = standardInventory.getOrDefault(product.getName(), 0);
            temp ++;
            if(standardInventory.containsKey(product.getName())) {
                standardInventory.replace(product.getName(), temp);
            }else{
                standardInventory.put(product.getName(), temp);
            }
        }
        return standardInventory;
    }

    private static List<Product> fillFridge(){
        Product milk1 = Product.MILK;
        Product milk2 = Product.MILK;
        Product butter =Product.BUTTER;
        Product zucchini1  = Product.ZUCCHINI;
        Product zucchini2  = Product.ZUCCHINI;
        Product zucchini3  = Product.ZUCCHINI;
        Product tomato1 = Product.TOMATO;
        Product tomato2 = Product.TOMATO;
        Product tomato3 = Product.TOMATO;
        Product egg1 = Product.EGG;
        Product egg2 = Product.EGG;
        Product egg3 = Product.EGG;
        Product egg4 = Product.EGG;
        Product egg5 = Product.EGG;
        Product egg6 = Product.EGG;
        Product whiskey = Product.WHISKEY;
        Product vodka = Product.VODKA;
        Product gin = Product.GIN;
        Product beer1 = Product.BEER;
        Product beer2 = Product.BEER;
        Product beer3 = Product.BEER;

        LinkedList<Product> fridgeInventory = new LinkedList<>();
        fridgeInventory.add(milk1);
        fridgeInventory.add(milk2);
        fridgeInventory.add(butter);
        fridgeInventory.add(zucchini1);
        fridgeInventory.add(zucchini2);
        fridgeInventory.add(zucchini3);
        fridgeInventory.add(tomato1);
        fridgeInventory.add(tomato2);
        fridgeInventory.add(tomato3);
        fridgeInventory.add(egg1);
        fridgeInventory.add(egg2);
        fridgeInventory.add(egg3);
        fridgeInventory.add(egg4);
        fridgeInventory.add(egg5);
        fridgeInventory.add(egg6);
        fridgeInventory.add(whiskey);
        fridgeInventory.add(vodka);
        fridgeInventory.add(gin);
        fridgeInventory.add(beer1);
        fridgeInventory.add(beer2);
        fridgeInventory.add(beer3);
        return Collections.synchronizedList(fridgeInventory);
    }




}
