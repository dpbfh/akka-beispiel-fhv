package com.lab3.enviroment;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.DispatcherSelector;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.lab3.IMessage;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//Encapsulate Sensor and Envoirment
public class WheatherSimulator extends AbstractBehavior<WheatherMessage> {
    private ActorRef<IMessage> replyTo;
    private final FiniteDuration SCHEDULED_DUR = new FiniteDuration(600,TimeUnit.SECONDS);
    private static List<ActorRef<IMessage>> responselist = new ArrayList<>();
    public static Behavior<WheatherMessage> create(ActorRef<IMessage> replyTo){
        return Behaviors.setup(context -> new WheatherSimulator(context, replyTo));
    }

    private WheatherSimulator(ActorContext<WheatherMessage> context, ActorRef<IMessage> replyTo) {
        super(context);
        this.replyTo = replyTo ;
        onReadWeather(new WheatherMessage());
    }

    @Override
    public Receive<WheatherMessage> createReceive() {
        return newReceiveBuilder().onMessage(WheatherMessage.class, this::onReadWeather).build();
    }

    private Behavior<WheatherMessage> onReadWeather(WheatherMessage value){
        if(!value.isAnwser()){
             var message = new WheatherMessage();
             message.setSunny(getRandomBool());
             getContext().getSystem().scheduler().scheduleAtFixedRate(SCHEDULED_DUR,SCHEDULED_DUR,()-> getContext().getSelf().tell(message),getContext().getSystem().dispatchers().lookup(DispatcherSelector.defaultDispatcher()));
             value.setAnwser(true);
             replyTo.tell(value);
        }

        return Behaviors.same();
    }

    //30 PRozent wahrscheinlichkeit für true
    private  boolean getRandomBool(){

        return new Random().nextInt(10) % 4 == 0;
    }

    public static  void InformmePlease(ActorRef<IMessage> respondTo) {
        responselist.add(respondTo);
    }


}
