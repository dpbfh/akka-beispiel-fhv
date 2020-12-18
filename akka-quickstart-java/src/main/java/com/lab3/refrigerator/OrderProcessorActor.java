package com.lab3.refrigerator;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class OrderProcessorActor extends AbstractBehavior<OrderMessage> {
        public final ActorRef<IFridgeMessage> parentActor;

        private OrderProcessorActor(ActorContext<OrderMessage> context, ActorRef<IFridgeMessage> messageActorRef){
            super(context);
            parentActor = messageActorRef;
        }

        public static Behavior<OrderMessage> create(ActorRef <IFridgeMessage> messageActorRef) {
            return Behaviors.setup(context -> new OrderProcessorActor(context, messageActorRef));
        }

        @Override
        public Receive<OrderMessage> createReceive() {
            return newReceiveBuilder()
                    .onMessage(OrderMessage.class, this::startOrder).build();
        }

        private Behavior<OrderMessage> startOrder(OrderMessage orderMessage) {
            if(!Objects.equals(orderMessage.getErrorMsg(), "")){
                System.out.println(orderMessage.getErrorMsg());
                var msg = new OrderProcessorResponseMessage();
                msg.setErrorMsg(orderMessage.getErrorMsg());
                parentActor.tell(msg);
                return Behaviors.stopped();
            }
            //Einkaufen dauert
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("OrderProcessor Startet");
            List<Product> productList = new LinkedList<>();
            for (int i = 0; i < orderMessage.getAmount(); i++) {
                productList.add(orderMessage.getProduct());
            }
            double totalPrize = orderMessage.getProduct().getPrize() * orderMessage.getAmount();
            Receipt receipt = createReceipt(orderMessage.getProduct(), orderMessage.getAmount(), totalPrize);
            var msg = new OrderProcessorResponseMessage();
            msg.setReceipt(receipt);
            msg.setProductList(Collections.unmodifiableList(productList));
            parentActor.tell(msg);
            System.out.println("OrderProcessor Stop");

            return Behaviors.stopped();
        }

            private  Receipt createReceipt(Product product, int amount, double totalprize){
        return new Receipt(LocalDateTime.now(), product, amount, totalprize);
    }

}

