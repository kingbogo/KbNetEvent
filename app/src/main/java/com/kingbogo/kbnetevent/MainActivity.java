package com.kingbogo.kbnetevent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kingbogo.netevent.NetEventBus;
import com.kingbogo.netevent.annotation.NetEvent;
import com.kingbogo.netevent.data.EventMode;
import com.kingbogo.netevent.data.NetType;
import com.kingbogo.netevent.util.KbLogUtil;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        NetEventBus.getInstance().registerObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetEventBus.getInstance().unRegisterObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetEventBus.getInstance().release();
    }

    /**
     * eventMode指网络订阅模式，默认订阅所有网络变化情况。
     *
     * @param netType 当前的网络类型
     */
    @NetEvent(eventMode = EventMode.AUTO)
    public void onNetChanged(NetType netType) {
        KbLogUtil.i(TAG, "_onNetChanged(), 当前网络变化netType -----> " + netType);
    }

    //    /**
    //     * eventMode指网络订阅模式，EventMode.CONNECT
    //     *
    //     * @param netType 当前的网络类型
    //     */
    //    @NetEvent(eventMode = EventMode.CONNECT)
    //    public void onNetConnected(NetType netType) {
    //        KbLogUtil.i(TAG, "_onNetConnected(), 当前连接网络netType -----> " + netType);
    //    }
    //
    //    /**
    //     * eventMode指网络订阅模式，EventMode.DISCONNECT
    //     *
    //     * @param netType 当前的网络类型
    //     */
    //    @NetEvent(eventMode = EventMode.DISCONNECT)
    //    public void onNetDisconnect(NetType netType) {
    //        KbLogUtil.i(TAG, "_onNetDisconnect(), 当前断开网络netType -----> " + netType);
    //    }

}
