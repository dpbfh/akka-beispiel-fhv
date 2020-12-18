package com.lab3.service;

import akka.actor.typed.ActorSystem;
import com.lab3.*;
import com.lab3.dtos.*;
import com.lab3.enviroment.TempretureMessage;
import com.lab3.heating.ACMessage;
import com.lab3.media.MediaMessage;
import com.lab3.refrigerator.*;

import java.util.List;

public class HomeAutomationWebservice {
    //#actor-system
    private static  ActorSystem<IMessage> blackboard;
    private static  HomeAutomationWebservice instance;

    private HomeAutomationWebservice(){
        //#actor-system
        blackboard = ActorSystem.create(Blackboard.create(), "BlackBoard");

//        OrderMessage orderMessage = new OrderMessage();
//        orderMessage.setProduct(Product.WHISKEY);
//        orderMessage.setAmount(2);
//        blackboard.tell(orderMessage);
//        HistoryOfOrdersMessage historyOfOrdersMessage = new HistoryOfOrdersMessage();
//        blackboard.tell(historyOfOrdersMessage);
//        System.out.println("\n\n\n.\n");
//        StoredProductsMessage storedProductsMessage = new StoredProductsMessage();
//        blackboard.tell(storedProductsMessage);

    }

    public static HomeAutomationWebservice getInstance(){
        if(instance == null){
            //noinspection InstantiationOfUtilityClass
            instance = new HomeAutomationWebservice();
        }
        return instance;
    }
    
    public void CloseBlinds(){
        var message = new BlindsMessage();
        message.Close();
        blackboard.tell(message);

//        ConsumeMessage consumeMessage = new ConsumeMessage();
//        consumeMessage.setProduct(Product.EGG);
//        blackboard.tell(consumeMessage);
//
//
//        StoredProductsMessage storedProductsMessage = new StoredProductsMessage();
//        blackboard.tell(storedProductsMessage);

    }

    public void OpenBlinds() {
        var message = new BlindsMessage();
        message.Open();
        blackboard.tell(message);
    }

    public void PowerOnAc(){
        var message = new ACMessage();
        message.SwitchOn();
        blackboard.tell(message);
    }

    public void StopAC(){
        var message = new ACMessage();
        message.SwitchOff();
        blackboard.tell(message);
    }

    public void StartMediaStation(){
        var message = new MediaMessage();
        message.setOn(true);
        blackboard.tell(message);
    }

    public void StopMediaStation(){
        var message = new MediaMessage();
        message.setOn(false);
        blackboard.tell(message);
    }

    public void setTemperature(TempratureUnits unit, double degree){
        var tempMessage = new TempretureMessage();
        tempMessage.setTemperature(unit,degree);
        blackboard.tell(tempMessage);
    }

    public double getTemperature(TempratureUnits units){
        return  Blackboard.getHomestate().getTemperature(units);
    }

    public ImuttableHomeState getStateOfHome(){
        return Blackboard.getHomestate();
    }

    public List<ProductDto> getFridgeInventory(){
        var message = new StoredProductsMessage();
        //HACK wait until ConsumeMessageanwser Changed in state
        var oldmsg = Blackboard.getFridgeState().getStoredProductsMessage();
        blackboard.tell(message);

        //noinspection StatementWithEmptyBody
        while(oldmsg == Blackboard.getFridgeState().getStoredProductsMessage());
        return Blackboard.getFridgeState().getStoredProductsMessage().getInventory();
    }

    public List<ReceiptDto> getFridgeOrderReceipts(){
        var historyMessage = new HistoryOfOrdersMessage();

        //HACK wait until ConsumeMessageanwser Changed in state
        var oldmsg = Blackboard.getFridgeState().getHistoryOfOrdersMessage();
        blackboard.tell(historyMessage);

        //noinspection StatementWithEmptyBody
        while(oldmsg == Blackboard.getFridgeState().getHistoryOfOrdersMessage());
        return Blackboard.getFridgeState().getHistoryOfOrdersMessage().getReceiptList() ;
    }

    public ConsumeMessageDto consumeProduct(ProductDto productdto) {
        var consumeMessage = new ConsumeMessage();
        var product= Product.getByName(productdto.getName());
        consumeMessage.setProduct(product);
        blackboard.tell(consumeMessage);




        //noinspection StatementWithEmptyBody
        var msg = new ConsumeMessageDto();
        msg.setProduct(productdto);
        return msg;
    }

    public ConsumeMessageDto getLastConsumeMessage() {
        return Blackboard.getFridgeState().getLastConsumeMessage();
    }

    public void orderProduct(ProductDto productdto,int amount) {

        var storedProductMessage = new OrderMessage();
        var product= Product.getByName(productdto.getName());
        storedProductMessage.setProduct(product);
        storedProductMessage.setAmount(amount);
        blackboard.tell(storedProductMessage);


    }

    public StoredProductsMessageDto getLastStoredMessage() {
        return Blackboard.getFridgeState().getStoredProductsMessage();
    }

    public OrderMessageDto getLastOrderMessage() {

        return Blackboard.getFridgeState().getLastOrderMessage();
    }
}
