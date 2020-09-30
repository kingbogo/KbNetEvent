package com.kingbogo.netevent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.kingbogo.netevent.core.KbNetDispatcher;
import com.kingbogo.netevent.core.KbNetworkCallbackImpl;
import com.kingbogo.netevent.core.KbNetworkStateReceiver;
import com.kingbogo.netevent.data.KbConstants;
import com.kingbogo.netevent.util.KbCheckUtil;

/**
 * <p>
 * NetEventBus
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public class NetEventBus {

    private Context mApplicationContext;
    private KbNetDispatcher mNetDispatcher;

    // >= 5.0
    private ConnectivityManager mConnectivityManager;
    private KbNetworkCallbackImpl mNetworkCallback;

    // <5.0
    private KbNetworkStateReceiver mNetworkStateReceiver;

    private NetEventBus() {
        mNetDispatcher = new KbNetDispatcher();
    }

    private static volatile NetEventBus mInstance;

    public static NetEventBus getInstance() {
        if (mInstance == null) {
            synchronized (NetEventBus.class) {
                if (mInstance == null) {
                    mInstance = new NetEventBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param applicationContext 应用上下文
     */
    @SuppressLint("MissingPermission")
    public void init(Context applicationContext) {
        mApplicationContext = applicationContext;
        if (isHigherThenLollipop()) {
            if (mNetworkCallback == null) {
                mNetworkCallback = new KbNetworkCallbackImpl(mNetDispatcher);
                NetworkRequest request = new NetworkRequest.Builder().build();
                mConnectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (mConnectivityManager != null) {
                    boolean hasPermission4Network = KbCheckUtil.checkHasPermission(NetEventBus.getInstance().getApplicationContext(), "android.permission.ACCESS_NETWORK_STATE");
                    if (hasPermission4Network) {
                        mConnectivityManager.registerNetworkCallback(request, mNetworkCallback);
                    }
                }
            }
        } else {
            // 5.0以下：广播方式
            if (mNetworkStateReceiver == null) {
                mNetworkStateReceiver = new KbNetworkStateReceiver(mNetDispatcher);
                IntentFilter filter = new IntentFilter();
                filter.addAction(KbConstants.NET_CHANGE_ACTION);
                mApplicationContext.registerReceiver(mNetworkStateReceiver, filter);
            }
        }
    }

    /**
     * 释放
     */
    public void release() {
        // 清空所有缓存
        unRegisterAllObserver();
        // 反注册
        if (isHigherThenLollipop()) {
            if (mNetworkCallback != null && mConnectivityManager != null) {
                mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
                mNetworkCallback = null;
                mConnectivityManager = null;
            }
        } else {
            if (mNetworkStateReceiver != null) {
                if (mApplicationContext != null) {
                    mApplicationContext.unregisterReceiver(mNetworkStateReceiver);
                }
            }
        }
    }

    /**
     * 注册
     */
    public void registerObserver(Object observer) {
        mNetDispatcher.registerObserver(observer);
    }

    /**
     * 反注册
     */
    public void unRegisterObserver(Object observer) {
        mNetDispatcher.unRegisterObserver(observer);
    }

    public Context getApplicationContext() {
        if (mApplicationContext == null) {
            throw new RuntimeException("mApplicationContext is empty");
        }
        return mApplicationContext;
    }

    /**
     * 版本是否是5.0及以上
     *
     * @return true/false
     */
    private boolean isHigherThenLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private void unRegisterAllObserver() {
        mNetDispatcher.unRegisterAllObserver();
    }

}
