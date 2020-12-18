package com.lab3.heating;

import com.lab3.IMessage;

public class ACMessage implements IMessage {
    private boolean switchOn;
    private boolean isActive;

    public boolean isSwitchOn() {
        return switchOn;
    }

    public void SwitchOn() {
        this.switchOn = true;
    }

    public void SwitchOff() {
        this.switchOn = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean anwser = false;

    @Override
    public void setAnwser( boolean x ) {
        anwser = x;
    }

    @Override
    public boolean isAnwser() {
        return anwser;
    }

    @Override
    public String toString() {
        if(!anwser){
            return "Requested to" + (switchOn?"to switch On":"to switch Off") + "AC";
        }else{
            return "Ac " + (isActive?"is On":"is Off");
        }
    }
}
