package com.konka.demo.onlineconfig;


import com.alibaba.fastjson.annotation.JSONField;

public class NetConfigData {
    @JSONField(name = "appid")
    public int appid;
    @JSONField(name = "name")
    public String name;
    @JSONField(name = "version")
    public String version;
    @JSONField(name = "fileUrl")
    public String url;
    @JSONField(name = "md5Value")
    public String md5;
    @JSONField(name = "type")
    public int type;
    @JSONField(name = "updateDate")
    public String updateDate;

}
