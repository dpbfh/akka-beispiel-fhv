package com.lab3.heating;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.lab3.IMessage;


public class ACActor extends AbstractBehavior<ACMessage> {

    private boolean isOn = false;
    public final ActorRef<IMessage> replyTo;

    private ACActor(ActorContext<ACMessage> context, ActorRef <IMessage> messageActorRef) {
        super(context);
        this.replyTo = messageActorRef;
    }

    public static Behavior<ACMessage> create(ActorRef <IMessage> messageActorRef ) {
        return Behaviors.setup(context -> new ACActor(context,messageActorRef));
    }

    @Override
    public Receive<ACMessage> createReceive() {
        return newReceiveBuilder().onMessage(ACMessage.class, this::onGetMessage).build();
    }

    private Behavior<ACMessage> onGetMessage(ACMessage command) {
        if(!command.isAnwser()){
            this.isOn = command.isSwitchOn();
            var message = new ACMessage();
            message.setActive(this.isOn);
            message.setAnwser(true);
            replyTo.tell(message);
        }

        return Behaviors.same();
    }
}
