package com.alphaautoleak;

import com.alphaautoleak.antianticheats.AntiAntiCheat;
import com.alphaautoleak.events.EventReceiveMessage;
import com.alphaautoleak.events.EventSendMessage;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.Mod;


/**
 * @Auther: SnowFlake
 * @Date: 2022/1/30 01:28
 */
@Mod(modid = "fuck",version = "0.9999")
public class Bootstrap {
    public AntiCatAntiCheat antiCatAntiCheat;

    public Bootstrap()  {
        antiCatAntiCheat = new AntiCatAntiCheat();
        EventManager.register(this);
    }

    @EventTarget
    public void onSend(EventSendMessage sendMessage){

        for (AntiAntiCheat antiAntiCheat : antiCatAntiCheat.antiAntiCheatManager.antiAntiCheats)
        {
            antiAntiCheat.onSend(sendMessage.message,sendMessage);
        }

    }



    @EventTarget
    public void onReceive(EventReceiveMessage eventReceiveMessage){
        for (AntiAntiCheat antiAntiCheat : antiCatAntiCheat.antiAntiCheatManager.antiAntiCheats)
        {
            antiAntiCheat.onReceive(eventReceiveMessage.message,eventReceiveMessage);
        }
    }


}
