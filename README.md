# NetEvent

 [![Download](https://api.bintray.com/packages/kingbogo/maven/NetEvent/images/download.svg) ](https://bintray.com/kingbogo/maven/NetEvent/_latestVersion)

Android 网络状态变化监听库。5.0以上的使用ConnectivityManager实现，5.0以下的使用"广播"实现。

```
implementation 'com.github.kingbogo:netevent:{version}'
```

### 需要权限：
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 混淆规则
```
-keep class com.kingbogo.netevent.** {*;}
```



