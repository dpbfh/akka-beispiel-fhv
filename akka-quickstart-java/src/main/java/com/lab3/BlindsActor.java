package com.lab3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.concurrent.TimeUnit;


public class BlindsActor extends AbstractBehavior<BlindsMessage> {

    private boolean isDown = false;
    public final ActorRef<IMessage> replyTo;

    private BlindsActor(ActorContext<BlindsMessage> context, ActorRef <IMessage> messageActorRef) {
        super(context);
        this.replyTo = messageActorRef;
    }

    public static Behavior<BlindsMessage> create(ActorRef <IMessage> messageActorRef ) {
        return Behaviors.setup(context -> new BlindsActor(context,messageActorRef));
    }

    @Override
    public Receive<BlindsMessage> createReceive() {
        return newReceiveBuilder().onMessage(BlindsMessage.class, this::onGetMessage).build();
    }

    private Behavior<BlindsMessage> onGetMessage(BlindsMessage command) {
        if(!command.isAnwser()) {

            //Close or OpenRequest simulating 1 second to close or Open Blinds
            if (command.isCloseRequest()) {
                System.out.println("Blind is closing");
                if(!isDown){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        isDown = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Blind is closed");
                }

            } else if(command.isOpenRequest()){

                System.out.println("Blind is opening");
                if(isDown) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        isDown = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Blind is opened");
                }
            }

            var message = new BlindsMessage();
            message.setDown(this.isDown);
            message.setAnwser(true);
            replyTo.tell(message);
        }
        return Behaviors.same();
    }
}
