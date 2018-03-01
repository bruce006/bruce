package com.konka.demo.module.impl;


import com.konka.android.tv.KKPictureManager;
import com.konka.demo.module.AbstractCanAliasModule;
import com.konka.demo.module.EnumModuleState;

public class ColorWheel extends AbstractCanAliasModule {

    @Override
    public void setDemoState(EnumModuleState state) {
        if (EnumModuleState.ON == state) {
            KKPictureManager.getInstance(mContext).setColorWheelMode(
                    KKPictureManager.EN_KK_COLOR_WHEEL_MODE.ON);
        } else if (EnumModuleState.DEMO == state) {
            KKPictureManager.getInstance(mContext).setColorWheelMode(
                    KKPictureManager.EN_KK_COLOR_WHEEL_MODE.DEMO);
        } else if (EnumModuleState.OFF == state) {
            KKPictureManager.getInstance(mContext).setColorWheelMode(
                    KKPictureManager.EN_KK_COLOR_WHEEL_MODE.OFF);
        }
    }

    @Override
    public EnumModuleState getDemoState() {
        EnumModuleState ret = EnumModuleState.OFF;
        KKPictureManager.EN_KK_COLOR_WHEEL_MODE colorWheelMode = KKPictureManager.getInstance(
                mContext).getColorWheelMode();
        if (KKPictureManager.EN_KK_COLOR_WHEEL_MODE.ON == colorWheelMode) {
            ret = EnumModuleState.ON;
        } else if (KKPictureManager.EN_KK_COLOR_WHEEL_MODE.DEMO == colorWheelMode) {
            ret = EnumModuleState.DEMO;
        } else if (KKPictureManager.EN_KK_COLOR_WHEEL_MODE.OFF == colorWheelMode) {
            ret = EnumModuleState.OFF;
        }
        return ret;
    }

    @Override
    public boolean isSupport() {
        return true;
    }

}
