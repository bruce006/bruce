package com.konka.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.konka.android.tv.KKCommonManager;
import com.konka.demo.module.IDemoModule;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView bn1=(TextView)findViewById(R.id.bn1);
        bn1.setText(getApplication().getString(R.string.default_name_memc)+":开启");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String series = KKCommonManager.getInstance(getApplicationContext()).getSeries();
        ModuleCreator creator = ModuleCreator.getInstance();
        List<IDemoModule> modules = creator.create(getApplicationContext());
        for (IDemoModule module : modules) {
            Log.d("TEST", "module key = " + module.getModuleKey());
            Log.d("TEST", "module name = " + module.getDefaultName());
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
    }


}
