package com.konka.demo.onlineconfig;


import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.konka.android.tv.KKFactoryManager;
import com.lzy.okgo.OkGo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

class NetRequest {
    private static final String API_URL = "http://api.kkapp.com/OnlineKeyValueServer/onlineFile/getTvFileInfo.shtml?";
    private static final String APP_ID = "6";
    private static final String APP_KEY = "e87690dcc7f0d580afe72088c355008a";
    private static final String FILE_NAME = "config.txt";

    RequestResult getResult(Context context) {
        RequestResult result = null;
        Map<String, String> data = new HashMap<>();
        data.put("appId", APP_ID);
        data.put("appkey", APP_KEY);
        data.put("fileName", FILE_NAME);
        data.put("sn", getSerialNum(context));
        String sign = KonkaSignTool.doSign(data, "UTF-8");
        String url = API_URL + "appId=" + APP_ID + "&appkey=" + APP_KEY + "&sn=" +
                     getSerialNum(context) + "&fileName=" + FILE_NAME + "&sign=" + sign;
        try {
            Response response = OkGo.get(url).execute();
            String content = response.body().string();
            result = JSON.parseObject(content, RequestResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public InputStream getConfigFile(String baseUrl, String appendUrl) {
        InputStream is = null;
        try {
            Response response = OkGo.get(baseUrl + appendUrl).execute();
            is = response.body().byteStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    private String getSerialNum(Context context) {
        try {
            byte[] SerialNum = KKFactoryManager.getInstance(context).getSerialNumber();
            String fullSN = new String(SerialNum);
            return fullSN.substring(0, 20);
        } catch (Exception e) {
            return "";
        }
    }

}
