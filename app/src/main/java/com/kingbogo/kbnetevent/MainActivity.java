package com.kingbogo.kbnetevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

    @NetEvent(eventMode = EventMode.AUTO)
    public void onNetChanged(NetType netType) {
        KbLogUtil.i(TAG, "_onNetChanged(), netType -----> " + netType);
    }

    @NetEvent(eventMode = EventMode.DISCONNECT)
    public void onNetDisconnect(NetType netType) {
        KbLogUtil.i(TAG, "_onNetDisconnect(), 网络断开了，netType -----> " + netType);
    }

}
