package com.android.myutils.proxy;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 在Android 引入 JDK  /sun/misc/ProxyGenerator.java 生成动态代理类
 *
 */
public class ProxyUtils {
    private static final String TAG = "ProxyUtils";
    public static void generateClassFile(Class clazz, String proxyName) {
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, new Class[]{clazz});
        FileOutputStream out = null;
        File saveFile;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //获取SDCard目录
                File sdCardDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/test");
                if(!sdCardDir.exists()){
                    sdCardDir.mkdirs();
                }
                saveFile = new File(sdCardDir,  proxyName + ".class");
                Log.d(TAG, "generateClassFile: " + saveFile.getAbsolutePath());
                out = new FileOutputStream(saveFile);
                out.write(proxyClassFile);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
