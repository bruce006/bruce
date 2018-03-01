package com.konka.demo;

import android.content.Context;

import com.konka.demo.alias.AliasBean;
import com.konka.demo.module.IDemoModule;
import com.konka.demo.module.impl.ColorWheel;
import com.konka.demo.module.impl.DNR;
import com.konka.demo.module.impl.EQ;
import com.konka.demo.module.impl.GoldEye;
import com.konka.demo.module.impl.HDR;
import com.konka.demo.module.impl.LocalContrast;
import com.konka.demo.module.impl.LocalDimming;
import com.konka.demo.module.impl.MEMC;
import com.konka.demo.module.impl.SDR2HDR;
import com.konka.demo.onlineconfig.OnlineConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModuleCreator {
    private static final Object[][] MODULES_INIT_TABLE = {
            {MEMC.class, R.string.default_name_memc, R.string.default_desc_memc},
            {LocalDimming.class, R.string.default_name_local_dimming,
                    R.string.default_desc_local_dimming},
            {LocalContrast.class, R.string.default_name_local_contrast,
                    R.string.default_desc_local_contrast},
            {GoldEye.class, R.string.default_name_gold_eye, R.string.default_desc_gold_eye},
            {HDR.class, R.string.default_name_hdr, R.string.default_desc_hdr},
            {SDR2HDR.class, R.string.default_name_sdr2hdr, R.string.default_desc_sdr2hdr},
            {ColorWheel.class, R.string.default_name_color_wheel,
                    R.string.default_desc_color_wheel},
            {DNR.class, R.string.default_name_dnr, R.string.default_desc_dnr},
            {EQ.class, R.string.default_name_eq, R.string.default_desc_eq}};

    private static ModuleCreator sModuleCreator;
    private List<IDemoModule> mModules;

    public synchronized static ModuleCreator getInstance() {
        if (null == sModuleCreator) {
            sModuleCreator = new ModuleCreator();
        }
        return sModuleCreator;
    }

    private ModuleCreator() {
        mModules = new ArrayList<>();
    }


    public List<IDemoModule> create(Context context) {
        mModules.clear();
        Map<String, AliasBean> aliasBeanMap = OnlineConfig.getInstance(context).getAlias();
        for (Object[] moduleInitTable : MODULES_INIT_TABLE) {
            Class clazz = (Class) moduleInitTable[0];
            int defNameId = (int) moduleInitTable[1];
            int defDescId = (int) moduleInitTable[2];
            try {
                IDemoModule module = (IDemoModule) clazz.getConstructor(
                        new Class<?>[]{}).newInstance(new Object[]{});
                module.setContext(context);
                module.setDefaultName(defNameId);
                module.setDefaultDesc(defDescId);
                module.setAliasBean(aliasBeanMap.get(module.getModuleKey()));
                mModules.add(module);
				Log.d("TEST", "module name = " + module.getDefaultName());
				Log.d("TEST", "module alias name = " + module.getName(series));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        List<IDemoModule> ret = new ArrayList<>(mModules.size());
        ret.addAll(mModules);
        return ret;
    }

    public IDemoModule findDemoModule(String key) {
        for (IDemoModule module : mModules) {
            if (key.toLowerCase().equals(module.getModuleKey().toLowerCase())) {
                return module;
            }
        }
        return null;
    }

}
