package com.konka.demo.onlineconfig;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class RequestResult {
    @JSONField(name = "ret")
    public ResultCode resultCode;
    @JSONField(name = "data")
    public List<NetConfigData> configDataList;
    @JSONField(name = "ServerAddr")
    public String serverAddr;

}
