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

import java.util.Random;
import java.util.concurrent.TimeUnit;

//Encapsulate Sensor and Envoirment
public class TemperatureSimulator extends AbstractBehavior<IMessage> {
    private ActorRef<IMessage> replyTo;

    private boolean isACOn = false;
    private final FiniteDuration SCHEDULED_DUR = new FiniteDuration(600,TimeUnit.SECONDS);
    private static double temperature = 26; //Temperature in Celsius

    public static Behavior<IMessage> create(ActorRef<IMessage> replyTo){
        return Behaviors.setup(context -> new TemperatureSimulator(context, replyTo));
    }

    private TemperatureSimulator(ActorContext<IMessage> context, ActorRef<IMessage> replyTo) {
        super(context);
        this.replyTo = replyTo ;
        onReadTemperatureValue(new EnvTempMessage());
    }

    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder().onMessage(EnvTempMessage.class, this::onReadTemperatureValue).onMessage(TempretureMessage.class,this::onTempMessage).build();
    }

    private Behavior<IMessage> onTempMessage(TempretureMessage m) {
        if(!m.isAnwser()){
            var message = new EnvTempMessage();
            temperature = m.getTemperatureInC();
            message.setTempinC(temperature);
            replyTo.tell(message);
        }
        return Behaviors.same();
    }

    private Behavior<IMessage> onReadTemperatureValue(EnvTempMessage value){
        if(!value.isAnwser()){
            if(value.isACtoggleMessage()){
                System.out.println("Temperatursimulator informieren das AC status sich geändert hat");
                isACOn = !isACOn;
            }else{
                var message = new EnvTempMessage();
                temperature+=getRandom();
                message.setTempinC(temperature);
                getContext().getSystem().scheduler().scheduleAtFixedRate(SCHEDULED_DUR,SCHEDULED_DUR,()-> getContext().getSelf().tell(message),getContext().getSystem().dispatchers().lookup(DispatcherSelector.defaultDispatcher()));
                temperature = value.getTempinC();
                value.setAnwser(true);
                replyTo.tell(value);
            }
        }

        return Behaviors.same();
    }

    private  Double getRandom(){
        double r = new Random().nextDouble();
        if(new Random().nextBoolean()){
            r *= -1;
        }
        if(isACOn && r > 0){
            //Temperaur steigt maximal um 0.5 wenn AC ein ist
            r /=2;
        }
        return r;
    }



}
