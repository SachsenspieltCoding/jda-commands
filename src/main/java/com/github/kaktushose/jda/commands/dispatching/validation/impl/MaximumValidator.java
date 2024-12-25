package com.github.kaktushose.jda.commands.dispatching.validation.impl;

import com.github.kaktushose.jda.commands.annotations.constraints.Max;
import com.github.kaktushose.jda.commands.dispatching.InvocationContext;
import com.github.kaktushose.jda.commands.dispatching.validation.Validator;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link Validator} implementation that checks the {@link Max} constraint.
 *
 * @see Max
 * @since 2.0.0
 */
public class MaximumValidator implements Validator {

    /**
     * Validates an argument. The argument must be a number whose value must be lower or equal to the specified maximum.
     *
     * @param argument   the argument to validate
     * @param annotation the corresponding annotation
     * @param context    the corresponding {@link InvocationContext}
     * @return {@code true} if the argument is a number whose value is lower or equal to the specified maximum
     */
    @Override
    public boolean apply(@NotNull Object argument, @NotNull Object annotation, @NotNull InvocationContext<?> context) {
        Max max = (Max) annotation;
        return ((Number) argument).longValue() <= max.value();
    }
}
