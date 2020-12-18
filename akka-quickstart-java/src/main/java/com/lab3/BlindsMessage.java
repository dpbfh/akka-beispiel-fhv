package com.lab3;

public class BlindsMessage implements IMessage {
    private boolean isDown = false;
    private boolean close = false;
    private boolean anwser = false;


    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public void Close() {
        close = true;
    }

    public boolean isCloseRequest() {
        return close;
    }

    public void Open() {
        close = false;
    }

    public boolean isOpenRequest() {
        return !close;
    }
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
            return "Requested to" + (close?"Close":"Open") + "Blind";  //cool han garne gwusst dass des goht in nam string, grüße desp
        }else{
            return "Blind " + (close?"Closed":"Opened");
        }
    }
}
