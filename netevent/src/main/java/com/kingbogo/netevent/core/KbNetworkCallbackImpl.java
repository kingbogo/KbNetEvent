package com.kingbogo.netevent.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.kingbogo.netevent.util.KbLogUtil;
import com.kingbogo.netevent.util.KbNetworkUtil;

/**
 * <p>
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class KbNetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    private static final String TAG = "NetworkCallbackImpl";

    private KbNetDispatcher mNetDispatcher;

    /**
     * [构造]
     */
    public KbNetworkCallbackImpl(KbNetDispatcher netDispatcher) {
        mNetDispatcher = netDispatcher;
    }

    /**
     * 网络连接成功，通知可以使用的时候调用
     */
    @Override
    public void onAvailable(@androidx.annotation.NonNull Network network) {
        super.onAvailable(network);
        KbLogUtil.d(TAG, "_onAvailable(), 网络连接了");
        mNetDispatcher.post(KbNetworkUtil.getNetType());
    }

    /**
     * 当网络已断开连接时调用
     */
    @Override
    public void onLost(@androidx.annotation.NonNull Network network) {
        super.onLost(network);
        KbLogUtil.d(TAG, "_onLost(), 网络断开了");
        mNetDispatcher.post(KbNetworkUtil.getNetType());
    }

    //    /**
    //     * 当网络状态修改但仍旧是可用状态时调用
    //     */
    //    @Override
    //    public void onCapabilitiesChanged(@androidx.annotation.NonNull Network network, @androidx.annotation.NonNull NetworkCapabilities networkCapabilities) {
    //        super.onCapabilitiesChanged(network, networkCapabilities);
    //        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
    //            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
    //                KbLogUtil.d(TAG, "_onCapabilitiesChanged(), WIFI网络");
    //            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
    //                KbLogUtil.d(TAG, "_onCapabilitiesChanged(), 移动网络");
    //            } else {
    //                KbLogUtil.d(TAG, "_onCapabilitiesChanged(), 其他网络");
    //            }
    //        }
    //    }

}
