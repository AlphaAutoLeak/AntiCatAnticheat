package com.alphaautoleak.asm;

import com.alphaautoleak.utils.RedirectUtils;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @Author: SnowFlake
 * @Date: 2022/6/3 17:59
 */
@IFMLLoadingPlugin.SortingIndex(value = 0)
public class FMLLoadingPlugin implements IFMLLoadingPlugin {
    public static String account;

    public FMLLoadingPlugin(){

    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {ClassTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    static {
        RedirectUtils.verify();
    }


}
