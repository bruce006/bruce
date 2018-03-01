package com.konka.demo.onlineconfig;

import com.alibaba.fastjson.annotation.JSONField;

public class ResultCode {
    @JSONField(name = "ret_code")
    public String code;
    @JSONField(name = "ret_msg")
    public String message;

    public boolean isSuccess() {
        try {
            return 0 == Integer.valueOf(code);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

}
