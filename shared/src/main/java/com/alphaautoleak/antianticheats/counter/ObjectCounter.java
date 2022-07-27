package com.alphaautoleak.antianticheats.counter;

import java.util.Arrays;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 09:53
 */
public class ObjectCounter {
    public Object[] data;

    public int nullCount;
    public int byteArrayCount;
    public int stringCount;
    public int booleanCount;
    public int integerCount;
    public int objectCount;

    public ObjectCounter(Object[] data){
        this.data = data;

        for (Object obj : data)
        {
            if (obj == null)
            {
                this.nullCount++;
            }
            if ((obj instanceof byte[]))
            {
                this.byteArrayCount++;
            }
            if ((obj instanceof String))
            {
                this.stringCount++;
            }
            if ((obj instanceof Boolean))
            {
                this.booleanCount++;
            }
            if ((obj instanceof Integer))
            {
                this.integerCount++;
            }
            if (obj != null && !(obj instanceof String) && !(obj instanceof Integer) && !(obj instanceof Boolean) && !(obj instanceof byte[]) && !(obj instanceof Double) && !(obj instanceof Float) && !(obj instanceof Long)   )
            {
                this.objectCount++;
            }
        }
    }


    @Override
    public String toString() {
        return "ObjectCounter{" +
                "data=" + Arrays.toString(data) +
                ", nullCount=" + nullCount +
                ", byteArrayCount=" + byteArrayCount +
                ", stringCount=" + stringCount +
                ", booleanCount=" + booleanCount +
                ", integerCount=" + integerCount +
                ", objectCount=" + objectCount +
                '}';
    }


}
