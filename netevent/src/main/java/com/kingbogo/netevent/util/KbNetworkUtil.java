package com.kingbogo.netevent.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kingbogo.netevent.NetEventBus;
import com.kingbogo.netevent.annotation.NetEvent;
import com.kingbogo.netevent.data.NetType;

/**
 * <p>
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public final class KbNetworkUtil {

    public static final String TAG = "KbNetworkUtil";

    private KbNetworkUtil() {
    }

    /**
     * 网络是否可用
     */
    public static boolean isNetworkAvailable() {
        boolean hasPermission4Network = KbCheckUtil.checkHasPermission(NetEventBus.getInstance().getApplicationContext(), "android.permission.ACCESS_NETWORK_STATE");
        if (hasPermission4Network) {
            ConnectivityManager connectivityManager = (ConnectivityManager) NetEventBus.getInstance()
                    .getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            }
            @SuppressLint("MissingPermission") NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前的网络类型
     */
    public static NetType getNetType() {
        boolean hasPermission4Network = KbCheckUtil.checkHasPermission(NetEventBus.getInstance().getApplicationContext(), "android.permission.ACCESS_NETWORK_STATE");
        if (!hasPermission4Network) {
            return NetType.NONE;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) NetEventBus.getInstance()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            KbLogUtil.d(TAG, "_getNetType(), connectivityManager is null, so return NetType.NONE");
            return NetType.NONE;
        }
        // 获取当前激活的网络连接信息
        @SuppressLint("MissingPermission") NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null) {
            KbLogUtil.d(TAG, "_getNetType(), info is null, so return NetType.NONE");
            return NetType.NONE;
        }
        int type = info.getType();
        KbLogUtil.d(TAG, "_getNetType(), type: " + type);
        if (type == ConnectivityManager.TYPE_MOBILE) {
            return NetType.MOBILE;
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

}
