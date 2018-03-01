package com.konka.demo.onlineconfig;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KonkaSignTool {
    private static final String KEY = "f388edf986f04b30b7f86c6519cc25a5";

    /**
     * 对参数进行MD5加签
     * @Title: doSign
     * @Description: 对参数进行MD5加签
     * @param sortedParams 需要加签的所有参数
     * @param charset
     * @return String
     */
    public static String doSign(Map<String, String> sortedParams, String charset) {
        try {
            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList<String>(sortedParams.keySet());
            Collections.sort(keys);
            int index = 0;
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = sortedParams.get(key);
                if (areNotEmpty(key, value)) {
                    content.append((index == 0 ? "" : "&") + key + "=" + value);
                    index++;
                }
            }
            String Server_Sign = MD5.MD5Encode(content+"&key="+KEY,charset).toUpperCase();
            return Server_Sign;
        } catch (Exception e) {
            //异常处理
        }
        return null;
    }

    private static boolean areNotEmpty(String... strings) {
        boolean ret = true;
        for (String s : strings) {
            if (null == s || s.isEmpty()) {
                ret = false;
            }
        }
        return ret;
    }

}
