package com.lab3;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class HomeState {
    private boolean acIsOn = false;
    private boolean mediaStationIsOn = false;
    private boolean blindIsClosed = false;
    private Double tempretureK = 0.0; //Temp in Kelvin
    private Double tempretureC = 0.0; //Temp in Celsius
    private Double tempretureF = 0.0; //Temp in Fahrenheit
    private boolean isSunny = false;

    public boolean isAcOn() {
        return acIsOn;
    }

    public void setAcIsOn(boolean acIsOn) {
        this.acIsOn = acIsOn;
    }

    public boolean isMediaStationOn() {
        return mediaStationIsOn;
    }

    public void setMediaStationIsOn(boolean mediaStationIsOn) {
        this.mediaStationIsOn = mediaStationIsOn;
    }

    public boolean isBlindClosed() {
        return blindIsClosed;
    }

    public void setBlindIsClosed(boolean blindIsClosed) {
        this.blindIsClosed = blindIsClosed;
    }

    public Double getTempretureK() {
        return tempretureK;
    }

    public void setTempretureK(Double tempretureK) {
        this.tempretureK = tempretureK;
    }

    public Double getTempretureC() {
        return tempretureC;
    }

    public void setTempretureC(Double tempretureC) {
        this.tempretureC = tempretureC;
    }

    public Double getTempretureF() {
        return tempretureF;
    }

    public void setTempretureF(Double tempretureF) {
        this.tempretureF = tempretureF;
    }

    public boolean isSunny() {
        return isSunny;
    }

    public void setSunny(boolean sunny) {
        isSunny = sunny;
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
