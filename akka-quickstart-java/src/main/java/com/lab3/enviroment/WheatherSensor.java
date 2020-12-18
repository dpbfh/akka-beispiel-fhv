package com.lab3.enviroment;

import akka.actor.typed.ActorRef;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import com.lab3.IMessage;

import java.time.Duration;

public class WheatherSensor extends AbstractBehavior<IMessage> {
    public WheatherSensor(ActorContext<IMessage> context, ActorRef<WheatherMessage> replyTo) {
        super(context);
    }

    @Override
    public Receive<IMessage> createReceive() {
        return null;
    }
}
