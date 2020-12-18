package com.lab3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.lab3.actors.ActorTyp;
import com.lab3.enviroment.*;
import com.lab3.heating.ACActor;
import com.lab3.heating.ACMessage;
import com.lab3.media.MediaMessage;
import com.lab3.media.MediaStationActor;
import com.lab3.refrigerator.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Blackboard extends AbstractBehavior<IMessage> {
    public static  HashMap<ActorTyp, ActorRef> _actors = new HashMap<>();
    private static HomeState homestate = new HomeState();
    private static FridgeState fridgeState = new FridgeState();

    public static ImuttableHomeState getHomestate() {
        return new ImuttableHomeState(homestate);
    }

    public static ImuttableFridgeState getFridgeState(){
        return new ImuttableFridgeState(fridgeState);
    }

    //Speichern alles Nachrichten für eventuelle Protokolierungszwecke
    private List<IMessage> messages = new LinkedList<>();


    private Blackboard(ActorContext<IMessage> context) {
        super(context);
        //Entspricht hinzufügen eines gerätes in den Smarthub
        _actors.put(ActorTyp.AC,getContext().spawn(ACActor.create(getContext().getSelf()), ActorTyp.AC.toString()));
        _actors.put(ActorTyp.MEDIASTATION,getContext().spawn(MediaStationActor.create(getContext().getSelf()), ActorTyp.MEDIASTATION.toString()));
        _actors.put(ActorTyp.BLINDS,getContext().spawn(BlindsActor.create(getContext().getSelf()), ActorTyp.BLINDS.toString()));
        _actors.put(ActorTyp.TEMPRETURE,getContext().spawn(TempSensor.create(getContext().getSelf()), ActorTyp.TEMPRETURE.toString()));
        //Ausnahme hier wäre nicht sinvoll wenn der simulator über das Blackboard Kommuniziert
        _actors.put(ActorTyp.TEMPSIMULATOR,getContext().spawn(TemperatureSimulator.create(_actors.get(ActorTyp.TEMPRETURE)), ActorTyp.TEMPSIMULATOR.toString()));
        //Sensor und Simulator in einem
        _actors.put(ActorTyp.WEATHER_SENSOR,getContext().spawn(WheatherSimulator.create(getContext().getSelf()), ActorTyp.WEATHER_SENSOR.toString()));
        _actors.put(ActorTyp.FRIDGE,getContext().spawn(FridgeActor.create(getContext().getSelf()), ActorTyp.FRIDGE.toString()));
        _actors.get(ActorTyp.FRIDGE).tell(new StoredProductsMessage());

    }


    public static Behavior<IMessage> create(){
        return Behaviors.setup(Blackboard::new);
    }


    @Override
    public Receive<IMessage> createReceive() {
        //Verteilt die nachrichten an den Richtigen ort
        return newReceiveBuilder().
                onMessage(TempretureMessage.class, this::onTempMessage).
                onMessage(MediaMessage.class, this::onMediaMessage).
                onMessage(ACMessage.class,this::onACMessage).
                onMessage(BlindsMessage.class,this::onBlindMessage).
                onMessage(WheatherMessage.class,this::onWheatherMessage).
                onMessage(OrderMessage.class,this::onOrderMessage).
                onMessage(ConsumeMessage.class,this::onConsumeMessage).
                onMessage(HistoryOfOrdersMessage.class, this::onHistoryOfOrders).
                onMessage(StoredProductsMessage.class, this::onStoredProducts).
                onAnyMessage(this::onMessageStore)
                .build();
    }



    private Behavior<IMessage> onHistoryOfOrders(HistoryOfOrdersMessage message){
        if(!message.isAnwser()) {
            _actors.get(ActorTyp.FRIDGE).tell(message);
        }else {
            fridgeState.setHistoryOfOrders(message);
        }
        return Behaviors.same();
    }

    private Behavior<IMessage> onStoredProducts(StoredProductsMessage message){
        if(!message.isAnwser()) {
            _actors.get(ActorTyp.FRIDGE).tell(message);
        }else{
            fridgeState.setStoredProductsMessage(message);
        }
        return Behaviors.same();
    }

    private Behavior<IMessage> onOrderMessage(OrderMessage message){
        if(!message.isAnwser()) {
            _actors.get(ActorTyp.FRIDGE).tell(message);
        }else {
            fridgeState.setLastOrderMessage(message);
        }
        return Behaviors.same();
    }

    private Behavior<IMessage> onConsumeMessage(ConsumeMessage message){
        if(!message.isAnwser()) {
            _actors.get(ActorTyp.FRIDGE).tell(message);
        }else {
            fridgeState.setLastConsumeMessage(message);
        }
        return Behaviors.same();
    }

    private Behavior<IMessage> onMessageStore(IMessage message) {
        messages.add(message);
        return Behaviors.same();
    }

    //Entspricht einer Controller funktion
    private Behavior<IMessage> onBlindMessage(BlindsMessage m){
        if(m.isAnwser()){
            homestate.setBlindIsClosed(m.isDown());
        }else {
            if(!homestate.isMediaStationOn() && !homestate.isSunny()){
                _actors.get(ActorTyp.BLINDS).tell(m);
            }
        }
        return Behaviors.same();
    }

    //Entspricht einer Controller funktion
    private  Behavior<IMessage> onMediaMessage(MediaMessage m) {
        if(m.isAnwser()){
            homestate.setMediaStationIsOn(m.isActive());
            System.out.println("Mediastation Active: "+homestate.isMediaStationOn());
            ActorRef<IMessage> ref = _actors.get(ActorTyp.BLINDS);
            var blind = new BlindsMessage();
            if(m.isActive() && !homestate.isBlindClosed() ){
                        blind.Close();
            }else{
                if(!homestate.isSunny()){
                    blind.Open();
                }
            }
            ref.tell(blind);
        }else{
            if(m.isOn() != homestate.isMediaStationOn()){
                _actors.get(ActorTyp.MEDIASTATION).tell(m);
            }
        }
        return Behaviors.same();
    }


    //Entspricht einer Controller funktion
    private  Behavior<IMessage> onACMessage(ACMessage m) {
        //ActorRef<IMessage> ref = _actors.get(ActorTyp.AC);
        if(m.isAnwser()){
            if(homestate.isAcOn() != m.isActive()){
                homestate.setAcIsOn(m.isActive());
                var acToggle = new EnvTempMessage();
                acToggle.setACtoggleMessage(true);
                _actors.get(ActorTyp.TEMPSIMULATOR).tell(acToggle);
            }
            System.out.println("AC Active: "+homestate.isAcOn());
        }else {
            // Hier Kann eine nicht Antwort nur von einer UserMessage stammen
            if(homestate.isAcOn() != homestate.isAcOn()) {
                _actors.get(ActorTyp.AC).tell(m);
            }        }
        return Behaviors.same();
    }

    //Entspricht einer Controller funktion
    private  Behavior<IMessage> onTempMessage(TempretureMessage m){
        if(m.isAnwser()){
            ActorRef<IMessage> ref = _actors.get(ActorTyp.AC);
            var message = new ACMessage();
            homestate.setTempretureC( m.getTemperatureInC());
            homestate.setTempretureK(m.getTemperatureInKelvin());
            homestate.setTempretureF(m.getTemperatureInFahrenheit());

            if(m.getTemperatureInC() > 25){
                if(!homestate.isAcOn()){
                    message.SwitchOn();
                    //Nur Nachricht senden bei zustandsänderung
                    ref.tell(message);
                }
            }else if(m.getTemperatureInC() < 22){
                if(homestate.isAcOn()){
                    message.SwitchOff();
                    //Nur Nachricht senden bei zustandsänderung
                    ref.tell(message);
                }
            }
            System.out.println(m.getTemperatureInC());
        }else {
            // Hier Kann eine nicht Antwort nur von einer UserMessage stammen
            _actors.get(ActorTyp.TEMPSIMULATOR).tell(m);
        }
        return Behaviors.same();
    }

    //Entspricht einer Controller funktion
    private Behavior<IMessage> onWheatherMessage(WheatherMessage m){
        if(m.isAnwser()){
            System.out.println(homestate.isSunny()?"Sonnig":"Nicht Sonnig");
            if(homestate.isSunny() != m.isSunny()){
                homestate.setSunny(m.isSunny());

                if(homestate.isSunny() && !homestate.isBlindClosed()){
                    var blindchange = new BlindsMessage();
                    blindchange.Close();
                    _actors.get(ActorTyp.BLINDS).tell(blindchange);

                }else {
                    if(!homestate.isSunny() && !homestate.isMediaStationOn()){
                        var blindchange = new BlindsMessage();
                        blindchange.Open();
                        _actors.get(ActorTyp.BLINDS).tell(blindchange);
                    }
                }
            }
        }else {
            // Hier Kann eine nicht Antwort nur von einer UserMessage stammen
            _actors.get(ActorTyp.WEATHER_SENSOR).tell(m);
        }
        return Behaviors.same();
    }



}
