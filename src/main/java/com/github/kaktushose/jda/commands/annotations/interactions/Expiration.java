package com.github.kaktushose.jda.commands.annotations.interactions;

import com.github.kaktushose.jda.commands.reflect.interactions.ExpirationDefinition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Expiration {

    ExpirationDefinition.Strategy value() default ExpirationDefinition.Strategy.CLEAR_ALL;

    String handler() default "";

    long time() default 15;

    TimeUnit unit() default TimeUnit.MINUTES;


}
