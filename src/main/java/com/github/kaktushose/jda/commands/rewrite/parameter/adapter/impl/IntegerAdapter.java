package com.github.kaktushose.jda.commands.rewrite.parameter.adapter.impl;

import com.github.kaktushose.jda.commands.rewrite.parameter.adapter.ParameterAdapter;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Optional;

public class IntegerAdapter implements ParameterAdapter<Integer> {

    @Override
    public Optional<Integer> parse(String raw, Guild guild) {
        try {
            return Optional.of(Integer.valueOf(raw));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }
}
