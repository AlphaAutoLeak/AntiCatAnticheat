package com.alphaautoleak.events;

import com.alphaautoleak.annotation.ReflectMark;
import com.darkmagician6.eventapi.events.Event;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/8 21:19
 */
public class EventReceiveMessage implements Event {
    private boolean cancelled;
    public Object message;

    public Object messageHandler;

    public EventReceiveMessage(Object packet,Object messagehandler){
        this.message = packet;
        this.messageHandler = messagehandler;
    }

    public boolean isCancelled() {
        return cancelled;
    }


    @ReflectMark(id = "2")
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }

}
