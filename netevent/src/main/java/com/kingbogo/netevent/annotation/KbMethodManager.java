package com.kingbogo.netevent.annotation;

import com.kingbogo.netevent.data.EventMode;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>
 * 符合要求的网络监听注解方法
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
public class KbMethodManager implements Serializable {

    private static final long serialVersionUID = -77016596962663209L;

    /** 被注解方法的参数类型 NetType netType */
    private Class<?> type;

    /** 需要监听的网络类型 (eventMode = EventMode.AUTO) */
    private EventMode eventMode;

    /** 需要执行的方法 */
    private Method method;

    public KbMethodManager(Class<?> type, EventMode eventMode, Method method) {
        this.type = type;
        this.eventMode = eventMode;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public EventMode getEventMode() {
        return eventMode;
    }

    public void setEventMode(EventMode eventMode) {
        this.eventMode = eventMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
