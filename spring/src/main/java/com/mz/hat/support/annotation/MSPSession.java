package com.mz.hat.support.annotation;

import com.mz.hat.support.interceptor.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MSPSession {

    UserRole role() default UserRole.USER;
}
