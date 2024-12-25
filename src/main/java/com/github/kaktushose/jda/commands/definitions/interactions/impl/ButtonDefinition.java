package com.github.kaktushose.jda.commands.definitions.interactions.impl;

import com.github.kaktushose.jda.commands.definitions.description.ClassDescription;
import com.github.kaktushose.jda.commands.definitions.description.MethodDescription;
import com.github.kaktushose.jda.commands.definitions.features.JDAEntity;
import com.github.kaktushose.jda.commands.definitions.interactions.Interaction;
import com.github.kaktushose.jda.commands.dispatching.events.interactions.ComponentEvent;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SequencedCollection;

public record ButtonDefinition(
        @NotNull ClassDescription clazz,
        @NotNull MethodDescription method,
        @NotNull Collection<String> permissions,
        @NotNull String label,
        @Nullable Emoji emoji,
        @Nullable String link,
        @NotNull ButtonStyle style
) implements Interaction, JDAEntity<Button> {

    @NotNull
    @Override
    public Button toJDAEntity() {
        String idOrUrl = Optional.ofNullable(link).orElse(definitionId());
        if (emoji == null) {
            return Button.of(style, idOrUrl, label);
        } else {
            return Button.of(style, idOrUrl, label, emoji);
        }
    }

    @NotNull
    @Override
    public String displayName() {
        return label.isEmpty() ? definitionId() : label;
    }

    @NotNull
    @Override
    public SequencedCollection<Class<?>> methodSignature() {
        return List.of(ComponentEvent.class);
    }

}
