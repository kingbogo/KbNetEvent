package com.kingbogo.netevent.annotation;

import com.kingbogo.netevent.data.EventMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * </p>
 *
 * @author Kingbo
 * @date 2020/9/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NetEvent {
    EventMode eventMode() default EventMode.AUTO;
}
