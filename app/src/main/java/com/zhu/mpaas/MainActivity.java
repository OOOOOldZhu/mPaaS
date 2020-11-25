package com.zhu.mpaas;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.alipay.mobile.framework.quinoxless.QuinoxlessPrivacyUtil;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.tbruyelle.rxpermissions3.RxPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

//        startActivity(new Intent(this, WebActivity.class));
        findViewById(R.id.main_btn1).setOnClickListener(v -> {
            MPNebula.startUrl("https://tech.antfin.com/");
        });
    }

    private void requestPermissions() {
        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
        };
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(PERMISSIONS)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        QuinoxlessPrivacyUtil.sendPrivacyAgreedBroadcast(MainActivity.this);
                    } else {
                        // Oups permission denied
                    }
                });
    }
}