package com.kingbogo.netevent.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kingbogo.netevent.data.KbConstants;
import com.kingbogo.netevent.util.KbLogUtil;
import com.kingbogo.netevent.util.KbNetworkUtil;

/**
 * <p>
 * 网络变化主播监听
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public class KbNetworkStateReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkStateReceiver";

    private KbNetDispatcher mNetDispatcher;

    public KbNetworkStateReceiver(KbNetDispatcher netDispatcher) {
        mNetDispatcher = netDispatcher;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (KbConstants.NET_CHANGE_ACTION.equals(intent.getAction())) {
            KbLogUtil.d(TAG, "_onReceive(), 网络发生了变化...");
            mNetDispatcher.post(KbNetworkUtil.getNetType());
        }
    }

}
