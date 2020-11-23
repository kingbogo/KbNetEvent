package com.kingbogo.netevent.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.kingbogo.netevent.annotation.KbMethodManager;
import com.kingbogo.netevent.annotation.NetEvent;
import com.kingbogo.netevent.data.EventMode;
import com.kingbogo.netevent.data.KbConstants;
import com.kingbogo.netevent.data.NetType;
import com.kingbogo.netevent.util.KbCheckUtil;
import com.kingbogo.netevent.util.KbLogUtil;
import com.kingbogo.netevent.util.KbNetworkUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网络派发者
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public class KbNetDispatcher implements Handler.Callback {

    public static final String TAG = "NetDispatcher";

    private Map<Object, List<KbMethodManager>> mNetworkMap;
    private Handler mHandler;

    public KbNetDispatcher() {
        mNetworkMap = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    /**
     * 注册
     */
    public void registerObserver(Object observer) {
        List<KbMethodManager> methodList = mNetworkMap.get(observer);
        if (methodList == null) {
            // add
            methodList = getAnnotationMethod(observer);
            if (!KbCheckUtil.isEmpty(methodList)) {
                mNetworkMap.put(observer, methodList);
            }
        }
    }

    /**
     * 反注册
     */
    public void unRegisterObserver(Object observer) {
        if (!mNetworkMap.isEmpty()) {
            mNetworkMap.remove(observer);
        }
    }

    /**
     * 清除所有注册
     */
    public void unRegisterAllObserver() {
        if (!mNetworkMap.isEmpty()) {
            mNetworkMap.clear();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 分发
     */
    public void post(NetType netType) {
        if (mHandler != null) {
            if (netType == NetType.NONE) {
                KbLogUtil.d(TAG, "无网络时，延时再检查一次");
                mHandler.sendEmptyMessageDelayed(KbConstants.WHAT_RE_CHECK_NETWORK, 150);
            } else {
                Message message = mHandler.obtainMessage(KbConstants.WHAT_POST);
                message.obj = netType;
                message.sendToTarget();
            }
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        int what = message.what;
        if (what == KbConstants.WHAT_POST) {
            NetType netType = (NetType) message.obj;
            post4MainThread(netType);

        } else if (what == KbConstants.WHAT_RE_CHECK_NETWORK) {
            NetType netType = KbNetworkUtil.getNetType();
            // 如果重新检测有网络时：再派发一次（解决网络切换时瞬间无网络的bug）
            post4MainThread(netType);
        }
        return false;
    }

    /**
     * 主线程中派发
     *
     * @param netType 网络类型
     */
    private void post4MainThread(NetType netType) {
        for (Map.Entry<Object, List<KbMethodManager>> entry : mNetworkMap.entrySet()) {
            Object observer = entry.getKey();
            List<KbMethodManager> methodManagerList = entry.getValue();
            for (KbMethodManager method : methodManagerList) {
                boolean isNeedInvoke = false;
                EventMode eventMode = method.getEventMode();
                switch (eventMode) {
                    case AUTO:
                        isNeedInvoke = true;
                        break;

                    case CONNECT:
                        isNeedInvoke = netType == NetType.WIFI || netType == NetType.MOBILE;
                        break;

                    case DISCONNECT:
                        isNeedInvoke = netType == NetType.NONE;
                        break;

                    default:
                        break;
                }
                // invoke
                if (isNeedInvoke) {
                    invoke(method, observer, netType);
                }
            }
        }
    }

    private void invoke(KbMethodManager methodManager, Object observer, NetType netType) {
        Method method = methodManager.getMethod();
        try {
            if (methodManager.getType() != null) {
                if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                    method.invoke(observer, netType);
                }
            } else {
                method.invoke(observer);
            }
        } catch (Exception ignored) {
            KbLogUtil.e(TAG, ignored);
        }
    }

    private List<KbMethodManager> getAnnotationMethod(Object observer) {
        List<KbMethodManager> methodList = new ArrayList<>();
        Method[] methods = observer.getClass().getDeclaredMethods();
        for (Method method : methods) {
            NetEvent netEvent = method.getAnnotation(NetEvent.class);
            if (netEvent == null) {
                continue;
            }
            KbLogUtil.d(TAG, "_getAnnotationMethod(), method: " + method.getName());
            // 方法参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "方法只能有一个参数");
            }
            // 参数类型校验
            String name = parameterTypes[0].getName();
            KbLogUtil.d(TAG, "_getAnnotationMethod(), paramName: " + name);
            if (NetType.class.getName().equals(name)) {
                KbMethodManager methodManager = new KbMethodManager(parameterTypes[0], netEvent.eventMode(), method);
                methodList.add(methodManager);
            }
        }
        return methodList;
    }

}
