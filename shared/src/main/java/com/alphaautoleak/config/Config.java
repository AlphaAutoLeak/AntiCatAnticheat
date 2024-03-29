package com.alphaautoleak.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/24 20:59
 */
public class Config {
    @Expose
    @SerializedName(value = "autoMode")
    public boolean autoMode;

    @Expose
    @SerializedName(value = "qqList")
    public ArrayList<Long> qqList;

    @Expose
    @SerializedName(value = "excludeList")
    public ArrayList<String> excludeList;

    @Expose
    @SerializedName(value = "cancelleScreenShot")
    public boolean cancelleScreenShot;

    @Expose
    @SerializedName(value = "enableCustomScreenShotText")
    public boolean enableCustomScreenShotText;

    @Expose
    @SerializedName(value = "screenShotText")
    public String screenShotText;

    @Expose
    @SerializedName(value = "macInfo")
    public String macInfo;

    @Expose
    @SerializedName(value = "macList")
    public byte[] mac; //size 6

    public Config() {

    }

    public Config(boolean autoMode, ArrayList<Long> qqList, ArrayList<String> excludeList, boolean cancelleScreenShot, boolean enableCustomScreenShotText, String screenShotText, String macInfo, byte[] mac) {
        this.autoMode = autoMode;
        this.qqList = qqList;
        this.excludeList = excludeList;
        this.cancelleScreenShot = cancelleScreenShot;
        this.enableCustomScreenShotText = enableCustomScreenShotText;
        this.screenShotText = screenShotText;
        this.macInfo = macInfo;
        this.mac = mac;
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    public void setAutoMode(boolean autoMode) {
        this.autoMode = autoMode;
    }

    public ArrayList<Long> getQqList() {
        return qqList;
    }

    public void setQqList(ArrayList<Long> qqList) {
        this.qqList = qqList;
    }

    public ArrayList<String> getExcludeList() {
        return excludeList;
    }

    public void setExcludeList(ArrayList<String> excludeList) {
        this.excludeList = excludeList;
    }

    public boolean isCancelleScreenShot() {
        return cancelleScreenShot;
    }

    public void setCancelleScreenShot(boolean cancelleScreenShot) {
        this.cancelleScreenShot = cancelleScreenShot;
    }

    public boolean isEnableCustomScreenShotText() {
        return enableCustomScreenShotText;
    }

    public void setEnableCustomScreenShotText(boolean enableCustomScreenShotText) {
        this.enableCustomScreenShotText = enableCustomScreenShotText;
    }

    public String getScreenShotText() {
        return screenShotText;
    }

    public void setScreenShotText(String screenShotText) {
        this.screenShotText = screenShotText;
    }

    public String getMacInfo() {
        return macInfo;
    }

    public void setMacInfo(String macInfo) {
        this.macInfo = macInfo;
    }

    public byte[] getMac() {
        return mac;
    }

    public void setMac(byte[] mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "Config{" +
                "autoMode=" + autoMode +
                ", qqList=" + qqList +
                ", excludeList=" + excludeList +
                ", cancelleScreenShot=" + cancelleScreenShot +
                ", enableCustomScreenShotText=" + enableCustomScreenShotText +
                ", screenShotText='" + screenShotText + '\'' +
                ", macInfo='" + macInfo + '\'' +
                ", mac=" + Arrays.toString(mac) +
                '}';
    }
}