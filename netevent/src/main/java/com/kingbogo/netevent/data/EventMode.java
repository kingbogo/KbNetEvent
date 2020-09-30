package com.kingbogo.netevent.data;

/**
 * <p>
 * 网络 订阅模式
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public enum EventMode {

    /** 默认模式: 所有网络变化都会回调 */
    AUTO,

    /** 连接模式：所有连接成功回调 */
    CONNECT,

    /** 断开模式：所有的断开回调 */
    DISCONNECT

}
