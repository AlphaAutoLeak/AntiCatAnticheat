package com.alphaautoleak.antianticheats;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.antianticheats.impl.HighVersionAnti;
import com.alphaautoleak.antianticheats.impl.MidVersionAnti;
import com.alphaautoleak.antianticheats.impl.LowVersionAnti;

import java.util.ArrayList;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/8 20:45
 */
public class AntiAntiCheatManager {

    public ArrayList<AntiAntiCheat> antiAntiCheats = new ArrayList<>();

    public AntiAntiCheatManager()
    {
        add(new LowVersionAnti());
        add(new HighVersionAnti());
        add(new MidVersionAnti());
    }


    public void add(AntiAntiCheat antiAntiCheat){
        antiAntiCheats.add(antiAntiCheat);
    }

}
