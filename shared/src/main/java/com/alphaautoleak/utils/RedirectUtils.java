package com.alphaautoleak.utils;

import com.alphaautoleak.CustomSecurityManager;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @Author: SnowFlake
 * @Date: 2022/6/6 12:18
 */
public class RedirectUtils {

    public static void verify(){
        if (true)
        {
            String fuck = Utils.md5(Utils.getHWID());

            String main = Utils.doGet("http://cn.aurorateam.online/cathwid.php");

            InetSocketAddress inetSocketAddress = new InetSocketAddress("45.133.119.151",110);

            //anti replace ip
            if (!inetSocketAddress.getAddress().getHostAddress().equals("45.133.119.151")) {
                return;
            }

            try {
                InputStream inputStream = new URL("http://cn.aurorateam.online/cathwid.php").openStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream,writer, StandardCharsets.UTF_8.name());

                String last = writer.toString();

                //check http contents
                if (last.equals(main))
                {
                    // hwid
                    StringBuilder sb = new StringBuilder();
                    String wtf = System.getenv("COMPUTERNAME") +
                            System.getProperty("os.name") +
                            System.getProperty("os.version") +
                            System.getProperty("os.arch") +
                            Runtime.getRuntime().availableProcessors();
                    try {
                        byte[] bytes = wtf.getBytes(StandardCharsets.UTF_8);
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        byte[] md5 = md.digest(bytes);
                        int i = 0;
                        for (byte b : md5) {
                            sb.append(Integer.toHexString((b & 0xFF) | 0x300), 0, 3);
                            if (i != md5.length - 1) {
                                sb.append("");
                            }
                            i++;
                        }
                    } catch (Exception ignored) {

                    }
                    //hwid
                    String hwid =  Utils.md5(sb.toString());

                    // check whether hwid method be modified
                    if (hwid.equals(fuck))
                    {
                        if (main.contains(fuck) && last.contains(hwid) && ReflectUtilies.contains(last,fuck) && ReflectUtilies.contains(main,hwid))
                        {
                            ReflectUtilies.setSecurityManager(new CustomSecurityManager());
                        }else{
                            while (true)
                            {
                                Thread.sleep(100);
                            }
                        }
                    }else{
                        while (true)
                        {
                            Thread.sleep(100);
                        }
                    }
                }else {
                    while (true)
                    {
                        Thread.sleep(100);
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

}
