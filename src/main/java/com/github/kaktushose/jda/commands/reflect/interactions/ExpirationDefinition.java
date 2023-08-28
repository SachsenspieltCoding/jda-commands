package com.github.kaktushose.jda.commands.reflect.interactions;

import com.github.kaktushose.jda.commands.annotations.interactions.Button;
import com.github.kaktushose.jda.commands.annotations.interactions.Expiration;
import com.github.kaktushose.jda.commands.annotations.interactions.Interaction;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class ExpirationDefinition {

    private static final ExpirationDefinition FALLBACK = new ExpirationDefinition(
            Strategy.CLEAR_ALL,
            15, TimeUnit.MINUTES,
            ""
    );
    private Strategy strategy;
    private long time;
    private TimeUnit unit;
    private String expirationHandler;

    private ExpirationDefinition(Strategy strategy, long time, TimeUnit unit, String expirationHandler) {
        this.strategy = strategy;
        this.time = time;
        this.unit = unit;
        this.expirationHandler = expirationHandler;
    }

    public static ExpirationDefinition build(@NotNull Method method) {
        if (!method.isAnnotationPresent(Button.class) ||
                !method.getDeclaringClass().isAnnotationPresent(Interaction.class) ||
                !method.isAnnotationPresent(Expiration.class)
        ) {
            return FALLBACK;
        }
        Expiration expiration = method.getAnnotation(Expiration.class);

        return new ExpirationDefinition(
                expiration.value(),
                expiration.time(),
                expiration.unit(),
                expiration.handler()
        );
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public String getExpirationHandler() {
        return expirationHandler;
    }

    public void setExpirationHandler(String expirationHandler) {
        this.expirationHandler = expirationHandler;
    }

    public enum Strategy {
        DELETE,
        CLEAR,
        CLEAR_ALL,
        KEEP
    }


}
