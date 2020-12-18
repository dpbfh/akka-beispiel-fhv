package com.lab3.media;

import com.lab3.IMessage;

public class MediaMessage implements IMessage {
    private boolean isOn;
    private boolean isActive;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
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
            return "Requested to" + (isOn?"to Turn On":"to Turn Off") + "Mediastation";
        }else{
            return "Mediastation " + (isActive?"is On":"is Off");
        }
    }
}
