package com.konka.demo.module;

import com.konka.demo.alias.AliasBean;

import android.content.Context;

public interface IDemoModule {
    void setContext(Context context);
    void setDefaultName(int resId);
    void setDefaultDesc(int resId);
    void setAliasBean(AliasBean aliasBean);
    void setDemoState(EnumModuleState state);
    String getModuleKey();
    String getDefaultName();
    String getDefaultDesc();
    String getName(String series);
    String getDesc(String series);
    EnumModuleState getDemoState();
    boolean isSupport();
}
