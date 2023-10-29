package com.github.kaktushose.jda.commands.dispatching.interactions.commands;

import com.github.kaktushose.jda.commands.JDACommands;
import com.github.kaktushose.jda.commands.dispatching.interactions.GenericEvent;
import com.github.kaktushose.jda.commands.dispatching.reply.ReplyContext;
import com.github.kaktushose.jda.commands.dispatching.reply.Replyable;
import com.github.kaktushose.jda.commands.reflect.interactions.commands.SlashCommandDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * This class is a subclass of {@link GenericEvent}.
 * It provides some additional features for sending messages and also grants
 * access to the {@link SlashCommandDefinition} object which describes the command that is executed.
 *
 * @author Kaktushose
 * @version 4.0.0
 * @see GenericEvent
 * @see Replyable
 * @since 1.0.0
 */
public class CommandEvent extends GenericEvent implements Replyable {

    private final SlashCommandDefinition command;
    private final CommandContext context;
    private final ReplyContext replyContext;

    /**
     * Constructs a CommandEvent.
     *
     * @param command the underlying {@link SlashCommandDefinition} object
     * @param context the {@link CommandContext}
     */
    public CommandEvent(@NotNull SlashCommandDefinition command, @NotNull CommandContext context) {
        super(GenericEvent.fromEvent(context.getEvent()));
        this.command = command;
        this.context = context;
        replyContext = new ReplyContext(context);
    }

    /**
     * Get the {@link SlashCommandDefinition} object which describes the command that is executed.
     *
     * @return the underlying {@link SlashCommandDefinition} object
     */
    public SlashCommandDefinition getCommandDefinition() {
        return command;
    }

    /**
     * Get the {@link JDACommands} object.
     *
     * @return the {@link JDACommands} object
     */
    public JDACommands getJdaCommands() {
        return context.getJdaCommands();
    }

    /**
     * Get the {@link CommandContext} object.
     *
     * @return the registered {@link CommandContext} object
     */
    @Override
    public CommandContext getContext() {
        return context;
    }

    @Override
    public ReplyContext getReplyContext() {
        return replyContext;
    }

    @Override
    public void reply() {
        replyContext.queue();
        context.getRuntime().setLatestReply(replyContext.toMessageCreateData());
    }
}
