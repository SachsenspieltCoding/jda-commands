package com.github.kaktushose.jda.commands.dispatching;

import com.github.kaktushose.jda.commands.JDACommands;
import com.github.kaktushose.jda.commands.reflect.CommandDefinition;
import com.github.kaktushose.jda.commands.reflect.ImplementationRegistry;
import com.github.kaktushose.jda.commands.settings.GuildSettings;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * This class models a command execution. The
 * {@link com.github.kaktushose.jda.commands.dispatching.parser.Parser Parser} constructs a new CommandContext for each
 * valid event received. The CommandContext is then passed through the execution chain until it is then transformed into
 * a {@link CommandEvent}.
 *
 * @author Kaktushose
 * @version 2.0.0
 * @since 2.0.0
 */
public class CommandContext {

    private String[] input;
    private MessageReceivedEvent event;
    private CommandDefinition command;
    private List<Object> arguments;
    private Message errorMessage;
    private GuildSettings settings;
    private ImplementationRegistry registry;
    private JDACommands jdaCommands;
    private boolean isHelpEvent;
    private boolean cancelled;

    /**
     * Gets the raw user input.
     *
     * @return the raw user input
     */
    public String[] getInput() {
        return input;
    }

    /**
     * Set the user input.
     *
     * @param input the user input
     * @return the current CommandContext instance
     */
    public CommandContext setInput(String[] input) {
        this.input = input;
        return this;
    }

    /**
     * Gets the corresponding {@link MessageReceivedEvent}.
     *
     * @return the corresponding {@link MessageReceivedEvent}
     */
    public MessageReceivedEvent getEvent() {
        return event;
    }

    /**
     * Set the {@link MessageReceivedEvent}.
     *
     * @param event the {@link MessageReceivedEvent}
     * @return the current CommandContext instance
     */
    public CommandContext setEvent(MessageReceivedEvent event) {
        this.event = event;
        return this;
    }

    /**
     * Gets the {@link CommandDefinition}.
     *
     * @return the {@link CommandDefinition}
     */
    public CommandDefinition getCommand() {
        return command;
    }

    /**
     * Set the {@link CommandDefinition}.
     *
     * @param command the {@link CommandDefinition}
     * @return the current CommandContext instance
     */
    public CommandContext setCommand(CommandDefinition command) {
        this.command = command;
        return this;
    }

    /**
     * Gets the parsed arguments.
     *
     * @return the parsed arguments
     */
    public List<Object> getArguments() {
        return arguments;
    }

    /**
     * Set the arguments.
     *
     * @param arguments the parsed arguments
     * @return the current CommandContext instance
     */
    public CommandContext setArguments(List<Object> arguments) {
        this.arguments = arguments;
        return this;
    }

    /**
     * Gets the {@link Message} to send if an error occurred.
     *
     * @return {@link Message} to send
     */
    public Message getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the the {@link Message} to send if an error occurred.
     *
     * @param message the {@link Message} to send
     * @return the current CommandContext instance
     */
    public CommandContext setErrorMessage(Message message) {
        this.errorMessage = message;
        return this;
    }

    /**
     * Gets the corresponding {@link GuildSettings}.
     *
     * @return the corresponding {@link GuildSettings}
     */
    public GuildSettings getSettings() {
        return settings;
    }

    /**
     * Set the {@link GuildSettings}.
     *
     * @param settings the {@link GuildSettings}
     * @return the current CommandContext instance
     */
    public CommandContext setSettings(GuildSettings settings) {
        this.settings = settings;
        return this;
    }

    /**
     * Gets the corresponding {@link ImplementationRegistry} instance.
     *
     * @return the corresponding {@link ImplementationRegistry} instance
     */
    public ImplementationRegistry getImplementationRegistry() {
        return registry;
    }

    /**
     * Set the {@link ImplementationRegistry} instance.
     *
     * @param registry the {@link ImplementationRegistry} instance
     * @return the current CommandContext instance
     */
    public CommandContext setImplementationRegistry(ImplementationRegistry registry) {
        this.registry = registry;
        return this;
    }

    /**
     * Gets the corresponding {@link JDACommands} instance.
     *
     * @return the corresponding {@link JDACommands} instance
     */
    public JDACommands getJdaCommands() {
        return jdaCommands;
    }

    /**
     * Set the {@link JDACommands} instance.
     *
     * @param jdaCommands the {@link JDACommands} instance
     * @return the current CommandContext instance
     */
    public CommandContext setJdaCommands(JDACommands jdaCommands) {
        this.jdaCommands = jdaCommands;
        return this;
    }

    /**
     * Whether the context represents a help event.
     *
     * @return {@code true} if the context represents a help event
     */
    public boolean isHelpEvent() {
        return isHelpEvent;
    }

    /**
     * Set whether the context represents a help event.
     *
     * @param helpEvent whether the context represents a help event
     * @return the current CommandContext instance
     */
    public CommandContext setHelpEvent(boolean helpEvent) {
        isHelpEvent = helpEvent;
        return this;
    }

    /**
     * Whether the context should be cancelled.
     *
     * @return {@code true} if the context should be cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Set whether the context should be cancelled.
     *
     * @param cancelled whether the context should be cancelled
     * @return the current CommandContext instance
     */
    public CommandContext setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }
}
