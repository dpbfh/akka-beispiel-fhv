package com.lab3;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ImuttableHomeState {
    private final boolean acIsOn;
    private final boolean mediaStationIsOn;
    private final boolean blindIsClosed;
    private final Double tempretureK ; //Temp in Kelvin
    private final Double tempretureC;
    private final Double tempretureF;
    private final boolean isSunny ;


    public ImuttableHomeState(HomeState state){
        this.acIsOn = state.isAcOn();
        this.mediaStationIsOn = state.isMediaStationOn();
        this.blindIsClosed = state.isBlindClosed();
        this.tempretureK = state.getTempretureK();
        this.tempretureC = state.getTempretureC();
        this.tempretureF = state.getTempretureF();
        this.isSunny = state.isSunny();
    }

    public boolean isAcOn() {
        return acIsOn;
    }


    public boolean isMediaStationOn() {
        return mediaStationIsOn;
    }


    public boolean isBlindClosed() {
        return blindIsClosed;
    }


    public Double getTempretureK() {
        return tempretureK;
    }


    public Double getTempretureC() {
        return tempretureC;
    }


    public Double getTempretureF() {
        return tempretureF;
    }


    public boolean isSunny() {
        return isSunny;
    }


    public double getTemperature(TempratureUnits unit) {
        switch (unit){
            case KELVIN:
                return getTempretureK();
            case FAHRENHEIT:
                return getTempretureF();
            default:
                return getTempretureC();

        }
    }
}
