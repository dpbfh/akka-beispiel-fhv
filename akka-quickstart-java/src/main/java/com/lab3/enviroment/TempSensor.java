package com.lab3.enviroment;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.lab3.IMessage;

public class TempSensor extends AbstractBehavior<IMessage> {
    private ActorRef<IMessage> replyTo;
    private boolean isACOn = false;

    public static Behavior<IMessage> create(ActorRef<IMessage> replyTo){
        return Behaviors.setup(context -> new TempSensor(context, replyTo));
    }

    private TempSensor(ActorContext<IMessage> context, ActorRef<IMessage> replyTo) {
        super(context);
        this.replyTo = replyTo ;
    }

    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder().onMessage(EnvTempMessage.class, this::onReadTemperatureValue).build();
    }

    private Behavior<IMessage> onReadTemperatureValue(EnvTempMessage value){

        var t = new TempretureMessage();
        t.setTemperatureInC(value.getTempinC());
        t.setAnwser(true);
        replyTo.tell(t);
        return Behaviors.same();
    }


}
