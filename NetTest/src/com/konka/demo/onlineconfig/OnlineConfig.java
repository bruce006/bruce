package com.konka.demo.onlineconfig;

import android.content.Context;
import android.content.SharedPreferences;

import com.konka.demo.alias.AliasBean;
import com.konka.demo.alias.AliasBeanParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class

OnlineConfig implements Runnable {
    private static final String CONFIG_NAME = "config.txt";
    private static final String CONFIG_DB_NAME = "config_db";
    private static final String CONFIG_VERSION = "version";
    private static final String CONFIG_MD5 = "md5";
    private static OnlineConfig sOnlineConfig;
    private WeakReference<Context> mContextRef;
    private ExecutorService mThread;
    private SharedPreferences mSP;

    public synchronized static OnlineConfig getInstance(Context context) {
        if (null == sOnlineConfig) {
            sOnlineConfig = new OnlineConfig(context);
        }
        return sOnlineConfig;
    }

    private OnlineConfig(Context context) {
        if (null != context.getApplicationContext()) {
            mContextRef = new WeakReference<>(context.getApplicationContext());
        } else {
            mContextRef = new WeakReference<>(context);
        }
        mThread = Executors.newSingleThreadExecutor();
        mSP = context.getSharedPreferences(CONFIG_DB_NAME, Context.MODE_PRIVATE);
    }

    public synchronized Map<String, AliasBean> getAlias() {
        Map<String, AliasBean> aliases = AliasBeanParser.parse(getConfigContent());
        mThread.execute(this);
        return aliases;
    }

    private String getConfigContent() {
        String ret;
        if (localConfigAlreadyExist()) {
            ret = getContentFromLocal();
        } else {
            ret = getContentFromAssets();
            copyAssetsToLocal();
        }
        return ret;
    }

    private boolean localConfigAlreadyExist() {
        boolean ret = false;
        File file = new File(mContextRef.get().getFilesDir() + "/" + CONFIG_NAME);
        if (file.exists()) {
            ret = true;
        }
        return ret;
    }

    private String getContentFromLocal() {
        String ret = "";
        try {
            FileInputStream is = mContextRef.get().openFileInput(CONFIG_NAME);
            ret = StreamHelper.toString(is);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return ret;
    }

    private String getContentFromAssets() {
        String ret = "";
        try {
            InputStream is = mContextRef.get().getAssets().open(CONFIG_NAME);
            ret = StreamHelper.toString(is);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return ret;
    }

    private void copyAssetsToLocal() {
        InputStream is;
        try {
            is = mContextRef.get().getAssets().open(CONFIG_NAME);
        } catch (IOException e) {
            is = null;
        }
        if (null != is) {
            try {
                FileOutputStream fos = mContextRef.get().openFileOutput(CONFIG_NAME,
                                                                        Context.MODE_PRIVATE);
                StreamHelper.output(is, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        final int localVersion = mSP.getInt(CONFIG_VERSION, 0);
        final String localMD5 = mSP.getString(CONFIG_MD5, "");
        NetRequest netRequest = new NetRequest();
        RequestResult result = netRequest.getResult(mContextRef.get());
        if (null == result || null == result.resultCode || !result.resultCode.isSuccess()) {
            return;
        }
        if (null == result.configDataList || result.configDataList.isEmpty()) {
            return;
        }
        NetConfigData configData = result.configDataList.get(0);
        int netVersion = 0;
        try {
            netVersion = Integer.valueOf(configData.version);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String netMD5 = configData.md5;
        if (localVersion > netVersion) {
            return;
        }
        if (localVersion == netVersion && localMD5.toLowerCase().equals(netMD5.toLowerCase())) {
            return;
        }
        // localVersion < netVersion, localMD5 != netMD5
        InputStream is = netRequest.getConfigFile(result.serverAddr, configData.url);
        if (null == is) {
            return;
        }
        removeLocalConfig();
        createLocalConfigFromNet(is);
        SharedPreferences.Editor editor = mSP.edit();
        editor.putInt(CONFIG_VERSION, netVersion);
        editor.putString(CONFIG_MD5, netMD5);
        editor.commit();
    }

    private void removeLocalConfig() {
        File file = new File(mContextRef.get().getFilesDir() + "/" + CONFIG_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    private void createLocalConfigFromNet(InputStream is) {
        try {
            FileOutputStream fos = mContextRef.get().openFileOutput(CONFIG_NAME,
                                                                    Context.MODE_PRIVATE);
            StreamHelper.output(is, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
