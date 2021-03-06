package com.konka.demo.module.impl;


import com.konka.android.system.KKConfigManager;
import com.konka.android.tv.KKPictureManager;
import com.konka.demo.module.AbstractCanAliasModule;
import com.konka.demo.module.EnumModuleState;

public class SDR2HDR extends AbstractCanAliasModule {

    @Override
    public void setDemoState(EnumModuleState state) {
        try {
            KKPictureManager.getInstance(mContext).setAdvancedPQState(
                    KKPictureManager.ADVANCED_PQ_TYPE_SDR2HDR, state.getValue());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public EnumModuleState getDemoState() {
        EnumModuleState ret = EnumModuleState.OFF;
        try {
            int state = KKPictureManager.getInstance(mContext).getAdvancedPQState(
                    KKPictureManager.ADVANCED_PQ_TYPE_SDR2HDR);
            if (0 <= state && state < EnumModuleState.values().length) {
                ret = EnumModuleState.values()[state];
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public boolean isSupport() {
        boolean ret = false;
        try {
            ret = KKConfigManager.getInstance(mContext).getBooleanConfig(
                    KKConfigManager.EN_KK_SYSTEM_CONFIG_KEY_BOOLEAN.SUPPORT_SDR2HDR);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ret;
    }

}
