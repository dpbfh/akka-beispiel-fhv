package com.lab3.media;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.lab3.IMessage;


public class MediaStationActor extends AbstractBehavior<MediaMessage> {

    private boolean isOn = false;
    public final ActorRef<IMessage> replyTo;

    private MediaStationActor(ActorContext<MediaMessage> context, ActorRef <IMessage> messageActorRef) {
        super(context);
        this.replyTo = messageActorRef;
    }

    public static Behavior<MediaMessage> create(ActorRef <IMessage> messageActorRef ) {
        return Behaviors.setup(context -> new MediaStationActor(context,messageActorRef));
    }

    @Override
    public Receive<MediaMessage> createReceive() {
        return newReceiveBuilder().onMessage(MediaMessage.class, this::onGetMessage).build();
    }

    private Behavior<MediaMessage> onGetMessage(MediaMessage command) {
        if(!command.isAnwser()) {
            this.isOn = command.isOn();
            System.out.println("Mediastation is "+(this.isOn?"On":"Off"));
            var message = new MediaMessage();
            message.setActive(this.isOn);
            message.setAnwser(true);
            replyTo.tell(message);
        }
        return Behaviors.same();
    }
}
