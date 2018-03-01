package com.konka.demo.module;

import com.konka.demo.alias.AliasBean;

import android.content.Context;

public abstract class AbstractCanAliasModule implements IDemoModule {
    protected Context mContext;
    private String mDefName;
    private String mDefDesc;
    private AliasBean mAliasBean;

    @Override
    public void setContext(Context context) {
        if (null != context.getApplicationContext()) {
            mContext = context.getApplicationContext();
        } else {
            mContext = context;
        }
    }

    @Override
    public void setDefaultName(int resId) {
        mDefName = mContext.getString(resId);
    }

    @Override
    public void setDefaultDesc(int resId) {
        mDefDesc = mContext.getString(resId);
    }

    @Override
    public void setAliasBean(AliasBean aliasBean) {
        mAliasBean = aliasBean;
    }

    @Override
    public String getModuleKey() {
        return getClass().getSimpleName();
    }

    @Override
    public String getDefaultName() {
        return mDefName;
    }

    @Override
    public String getDefaultDesc() {
        return mDefDesc;
    }

    @Override
    public String getName(String series) {
        String ret = mDefName;
        if (null != mAliasBean && null != mAliasBean.getNameBySeries(series)) {
            ret = mAliasBean.getNameBySeries(series);
        }
        return ret;
    }

    @Override
    public String getDesc(String series) {
        String ret = mDefName;
        if (null != mAliasBean && null != mAliasBean.getDescBySeries(series)) {
            ret = mAliasBean.getDescBySeries(series);
        }
        return ret;
    }
}
