package com.lab3.refrigerator;

import com.lab3.IMessage;

public interface IFridgeMessage extends IMessage {

    public String getErrorMsg();
    public void setErrorMsg(String msg);
}
