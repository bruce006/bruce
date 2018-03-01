package com.konka.demo.module.impl;


import com.konka.android.system.KKConfigManager;
import com.konka.demo.module.AbstractCanAliasModule;
import com.konka.demo.module.EnumModuleState;

public class EQ extends AbstractCanAliasModule {

    @Override
    public void setDemoState(EnumModuleState state) {

    }

    @Override
    public EnumModuleState getDemoState() {
        EnumModuleState ret = EnumModuleState.OFF;
        return ret;
    }

    @Override
    public boolean isSupport() {
        boolean ret = false;
        try {
            ret =  KKConfigManager.getInstance(mContext).getBooleanConfig(
                    KKConfigManager.EN_KK_SYSTEM_CONFIG_KEY_BOOLEAN.SUPPORT_ADVANCED_AUDIO_EFFECT);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ret;
    }

}
