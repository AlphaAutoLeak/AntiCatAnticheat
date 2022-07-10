package com.alphaautoleak.antianticheats;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.antianticheats.impl.LeastAntiCheat;
import com.alphaautoleak.antianticheats.impl.OldAntiCheat;

import java.util.ArrayList;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/8 20:45
 */
public class AntiAntiCheatManager {

    public ArrayList<AntiAntiCheat> antiAntiCheats = new ArrayList<>();

    public AntiAntiCheatManager()
    {
        if (AntiCatAntiCheat.oldVersion){
            antiAntiCheats.add(new OldAntiCheat());
        }
        antiAntiCheats.add(new LeastAntiCheat());
    }


}
