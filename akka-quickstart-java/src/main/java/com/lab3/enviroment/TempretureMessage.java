package com.lab3.enviroment;

import com.lab3.IMessage;
import com.lab3.TempratureUnits;

public class TempretureMessage implements IMessage {

    private boolean anwser = false;
    private static final double  CELSIUSTOKELVIN = 273.15;
    private Double temperatureInKelvin = 0d;
    private Double temperatureInC = 0d;
    private Double temperatureInFahrenheit = 0d;

    public TempretureMessage(){
        setTemperatureInC(25d);
    }
    @Override
    public void setAnwser( boolean x ) {
        anwser = x;
    }

    @Override
    public boolean isAnwser() {
        return anwser;
    }

    public Double getTemperatureInKelvin() {
        return temperatureInKelvin;
    }


    public Double getTemperatureInC() {
        return temperatureInC;
    }


    public Double getTemperatureInFahrenheit() {
        return temperatureInFahrenheit;
    }


    public void setTemperatureInFahrenheit(Double temperatureInFahrenheit) {
        this.temperatureInFahrenheit = temperatureInFahrenheit;
        this.temperatureInKelvin = (temperatureInFahrenheit - 32d) * 5d/9d + 273.15d;
        this.temperatureInC =  (temperatureInFahrenheit - 32d) * 5d/9d;
    }

    public void setTemperatureInC(Double temperatureInC) {
        this.temperatureInC = temperatureInC;
        this.temperatureInKelvin = temperatureInC +CELSIUSTOKELVIN;
        this.temperatureInFahrenheit = (temperatureInC * 9d/5d) + 32d;
    }

    public void setTemperatureInKelvin(Double temperatureInKelvin) {
        this.temperatureInKelvin = temperatureInKelvin;
        this.temperatureInC = temperatureInKelvin - CELSIUSTOKELVIN;
        this.temperatureInFahrenheit = (temperatureInKelvin - CELSIUSTOKELVIN) * 9d/5d + 32d;
    }


    public void setTemperature(TempratureUnits unit, double degree) {
        switch (unit){
            case KELVIN:
                setTemperatureInKelvin(degree);
                break;
            case CELSIUS:
                setTemperatureInC(degree);
                break;
            case FAHRENHEIT:
                setTemperatureInFahrenheit(degree);
                break;

        }
    }

    public double getTemperature(TempratureUnits unit) {
        switch (unit){
            case KELVIN:
                return getTemperatureInKelvin();
            case FAHRENHEIT:
                return getTemperatureInFahrenheit();
            default:
                return getTemperatureInC();

        }
    }


}
