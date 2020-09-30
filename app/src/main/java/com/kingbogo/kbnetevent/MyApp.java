package com.kingbogo.kbnetevent;

import android.app.Application;

import com.kingbogo.netevent.NetEventBus;

/**
 * <p>
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetEventBus.getInstance().init(this);
    }
}
