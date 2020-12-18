package com.lab3.enviroment;

import com.lab3.IMessage;

public class WheatherMessage implements IMessage {

    private boolean anwser = false;
    private boolean isSunny = false;

    public WheatherMessage(){

    }
    @Override
    public void setAnwser( boolean x ) {
        anwser = x;
    }

    @Override
    public boolean isAnwser() {
        return anwser;
    }

    public boolean isSunny() {
        return isSunny;
    }

    public void setSunny(boolean sunny) {
        isSunny = sunny;
    }
}
