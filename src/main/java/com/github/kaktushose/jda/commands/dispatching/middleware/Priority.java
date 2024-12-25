package com.github.kaktushose.jda.commands.dispatching.middleware;

import com.github.kaktushose.jda.commands.dispatching.middleware.internal.MiddlewareRegistry;

/**
 * Enum to define with witch priority a {@link Middleware} should be executed.
 *
 * @see MiddlewareRegistry
 * @since 4.0.0
 */
// !! order matters, because enums are compared by ordinal number (java.lang.Comparable)
public enum Priority {
    /**
     * Middlewares with priority PERMISSIONS will always be executed first
     */
    PERMISSIONS,
    /**
     * Highest priority for custom implementation, will be executed right after internal middlewares.
     */
    HIGH,
    /**
     * Default priority.
     */
    NORMAL,
    /**
     * Lowest priority, will be executed at the end
     */
    LOW
}
