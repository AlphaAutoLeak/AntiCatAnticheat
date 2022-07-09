package com.alphaautoleak.events;

import com.alphaautoleak.annotation.ReflectMark;
import com.darkmagician6.eventapi.events.Event;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelFutureListener;


import java.util.EnumMap;

/**
 * @Author: SnowFlake
 * @Date: 2022/6/3 20:25
 */
public class EventSendMessage implements Event {
    public Object message;
    public EnumMap<Side, FMLEmbeddedChannel> channels;
    public boolean cancelled;

    public EventSendMessage(Object iMessage, EnumMap channels){
        this.message = iMessage;
        this.channels = channels;
    }

    @ReflectMark(mark = "2")
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @ReflectMark(mark = "1")
    public void sendToServer(Object message)
    {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }


    public boolean isCancelled() {
        return this.cancelled;
    }
}
