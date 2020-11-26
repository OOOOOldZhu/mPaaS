package com.zhu.mpaas.activity;

import android.app.Application;
import android.content.Context;

import com.alipay.mobile.framework.quinoxless.IInitCallback;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.mpaas.nebula.adapter.api.MPNebulaOfflineInfo;
import com.zhu.mpaas.util.MyJSApiPlugin;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // mPaaS 初始化回调设置。
        QuinoxlessFramework.setup(this, new IInitCallback() {
            @Override
            public void onPostInit() {
                // 此回调表示 mPaaS 已经初始化完成，mPaaS 相关调用可在这个回调里进行。

                // 在这里开始使用mPaaS功能
                //调用registerCustomJsapi()完成自定义 JSAPI的注册。
                registerCustomJsapi();

                loadOfflineNebula();
            }
        });
    }

    private void registerCustomJsapi() {
        MPNebula.registerH5Plugin(
                // 插件的 class name
                MyJSApiPlugin.class.getName(),
                // 填空即可
                "",
                // 作用范围，填"page"即可
                "page",
                // 注册的 jsapi 名称
                new String[]{"myapi"});
    }

    private void loadOfflineNebula() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 此方法为阻塞调用，请不要在主线程上调用内置离线包方法。如果内置多个amr包，要确保文件已存在，如不存在，会造成其他内置离线包失败。
                // 此方法仅能调用一次，多次调用仅第一次调用有效。
                MPNebula.loadOfflineNebula("h5_json.json", new MPNebulaOfflineInfo("20201126_0.0.0.2.amr", "20201126", "0.0.0.2"));
            }
        }).start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // mPaaS 初始化。
        QuinoxlessFramework.init();
    }

}
