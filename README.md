# NetEvent

 [![Download](https://api.bintray.com/packages/kingbogo/maven/NetEvent/images/download.svg) ](https://bintray.com/kingbogo/maven/NetEvent/_latestVersion)

Android 网络状态变化监听库。5.0以上的使用ConnectivityManager实现，5.0以下的使用"广播"实现。

```
implementation 'com.github.kingbogo:netevent:{version}'
```

### 使用方法（参考Demo）：
1、App启动时初始化
```
   NetEventBus.getInstance().init(this);
```

2、注册：
```
   NetEventBus.getInstance().registerObserver(this);
```

3、反注册：
```
   NetEventBus.getInstance().unRegisterObserver(this);
```

4、监听：
```
    /**
     * eventMode指网络订阅模式，默认订阅所有网络变化情况。
     *
     * @param netType 当前的网络类型
     */
    @NetEvent(eventMode = EventMode.AUTO)
    public void onNetChanged(NetType netType) {
        KbLogUtil.i(TAG, "_onNetChanged(), netType -----> " + netType);
    }
```

5、释放资源：
```
  NetEventBus.getInstance().release();
```

### 需要权限：
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 混淆规则
```
-keep class com.kingbogo.netevent.** {*;}
```



