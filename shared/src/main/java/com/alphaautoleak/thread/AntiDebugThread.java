package com.alphaautoleak.thread;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SnowFlake
 * @Date: 2022/6/6 11:28
 */
public class AntiDebugThread
        extends Thread
{

    @Override
    public void run()
    {
        BufferedReader out = null;
        BufferedReader br = null;
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
            Process p = processBuilder.start();

            while (true)
            {

                out = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream()), Charset.forName("GB2312")));

                String ostr;
                while ((ostr = out.readLine()) != null)
                {
                    if (ostr.contains("HRSword.exe") ||
                            ostr.contains("ida64.exe") ||
                            ostr.contains("ida.exe") ||
                            ostr.contains("x32dbg.exe") ||
                            ostr.contains("x64dbg.exe") ||
                            ostr.contains("Wireshark.exe") ||
                            ostr.contains("Fiddler.exe") ||
                            ostr.contains("vmware.exe") ||
                            ostr.contains("VBoxService.exe")
                    ){
                        System.exit(0);
                    }
                }

                if (out != null)
                {
                    out.close();
                }
                sleep(1000);
            }
        }
        catch (Exception e)
        {

        }
    }


}
