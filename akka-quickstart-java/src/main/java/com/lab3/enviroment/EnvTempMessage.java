package com.lab3.enviroment;

import com.lab3.IMessage;

public class EnvTempMessage implements IMessage {
    private double tempinC = 25d;
    private boolean anwser = false;
    private boolean ACtoggleMessage = false;

    @Override
    public void setAnwser( boolean x ) {
        anwser = x;
    }

    @Override
    public boolean isAnwser() {
        return anwser;
    }


    public double getTempinC() {
        return tempinC;
    }

    public void setTempinC(double tempinC) {
        this.tempinC = tempinC;
    }

    public boolean isACtoggleMessage() {
        return ACtoggleMessage;
    }

    public void setACtoggleMessage(boolean ACtoggleMessage) {
        this.ACtoggleMessage = ACtoggleMessage;
    }
}
