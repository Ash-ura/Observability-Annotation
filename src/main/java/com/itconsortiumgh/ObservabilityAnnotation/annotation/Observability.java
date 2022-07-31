package com.itconsortiumgh.ObservabilityAnnotation.annotation;


import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Observability {

    LogLevel value() default LogLevel.INFO;

    String message() default "Observability default message";

    String key() default "Observability default key";

    boolean showArgs() default true;

}
