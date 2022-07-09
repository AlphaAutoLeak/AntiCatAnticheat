package com.alphaautoleak.antianticheats;

import java.util.Arrays;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 09:53
 */
public class PacketArray {
    public Object[] data;
    
    public PacketArray(Object[] data){
        this.data = data;
    }

    public int getNULLCount(){
        int count = 0;
        for (Object obj : data)
        {
            if (obj == null)
            {
                count++;
            }
        }
        return count;
    }

    public int getByteArrayTypeCount(){
        int count = 0;
        for (Object obj : data)
        {
            if ((obj instanceof byte[]))
            {
                count++;
            }
        }
        return count;
    }

    public int getStringTypeCount(){
        int count = 0;
        for (Object obj : data)
        {
            if ((obj instanceof String))
            {
                count++;
            }
        }
        return count;
    }

    public int getBooleanTypeCount(){
        int count = 0;
        for (Object obj : data)
        {
            if ((obj instanceof Boolean))
            {
                count++;
            }
        }
        return count;
    }

    public int getIntegerTypeCount(){
        int count = 0;
        for (Object obj : data)
        {
            if ((obj instanceof Integer))
            {
                count++;
            }
        }
        return count;
    }

    public int getObjectTypeCount(){
        int count = 0;
        for (Object obj : data)
        {
            if (obj != null && !(obj instanceof String) && !(obj instanceof Integer) && !(obj instanceof Boolean) && !(obj instanceof byte[]) && !(obj instanceof Double) && !(obj instanceof Float) && !(obj instanceof Long)   )
            {
                count++;
            }
        }
        return count;
    }


    @Override
    public String toString() {
        return "PacketArray{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
