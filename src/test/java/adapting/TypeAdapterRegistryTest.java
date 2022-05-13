package adapting;

import adapting.mock.JDACommandsMock;
import adapting.mock.MessageReceivedEventMock;
import adapting.mock.TypeAdapterRegistryTestController;
import com.github.kaktushose.jda.commands.JDACommands;
import com.github.kaktushose.jda.commands.dependency.DependencyInjector;
import com.github.kaktushose.jda.commands.dispatching.CommandContext;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;
import com.github.kaktushose.jda.commands.dispatching.GenericCommandEvent;
import com.github.kaktushose.jda.commands.dispatching.adapter.TypeAdapterRegistry;
import com.github.kaktushose.jda.commands.dispatching.adapter.impl.IntegerAdapter;
import com.github.kaktushose.jda.commands.dispatching.filter.FilterRegistry;
import com.github.kaktushose.jda.commands.dispatching.slash.SlashConfiguration;
import com.github.kaktushose.jda.commands.dispatching.validation.ValidatorRegistry;
import com.github.kaktushose.jda.commands.reflect.CommandDefinition;
import com.github.kaktushose.jda.commands.reflect.ImplementationRegistry;
import com.github.kaktushose.jda.commands.settings.GuildSettings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class TypeAdapterRegistryTest {

    private static Class<?> controller;
    private static TypeAdapterRegistryTestController instance;
    private static ValidatorRegistry validator;
    private static TypeAdapterRegistry adapter;
    private TypeAdapterRegistry registry;

    @BeforeAll
    public static void setup() {
        instance = new TypeAdapterRegistryTestController();
        controller = instance.getClass();
        validator = new ValidatorRegistry();
        adapter = new TypeAdapterRegistry();
    }

    @BeforeEach
    public void cleanup() {
        registry = new TypeAdapterRegistry();
    }

    @Test
    public void register_withNewTypeAndNewAdapter_ShouldAdd() {
        registry = new TypeAdapterRegistry();
        registry.register(CustomType.class, new CustomTypeAdapter());

        assertTrue(registry.exists(CustomType.class));
    }

    @Test
    public void register_withExistingTypeAndNewAdapter_ShouldOverride() {
        registry = new TypeAdapterRegistry();
        CustomTypeAdapter adapter = new CustomTypeAdapter();

        assertEquals(IntegerAdapter.class, registry.get(Integer.class).get().getClass());
        registry.register(Integer.class, adapter);

        assertEquals(adapter, registry.get(Integer.class).orElse(null));
    }

    @Test
    public void unregister_withExistingType_ShouldRemove() {
        registry = new TypeAdapterRegistry();

        assertTrue(registry.exists(Integer.class));
        registry.unregister(Integer.class);

        assertFalse(registry.exists(Integer.class));
    }

    @Test
    public void adapt_withStringArray_ShouldNotAdapt() throws NoSuchMethodException {
        CommandContext context = buildContext(buildCommand("stringArray", CommandEvent.class, String[].class), "a", "b", "c");

        registry.adapt(context);

        assertArrayEquals(new String[]{"a", "b", "c"}, (String[]) context.getArguments().get(1));
    }

    @Test
    public void adapt_withLessInputThanParameters_ShouldCancel() throws NoSuchMethodException {
        CommandContext context = buildContext(buildCommand("inputLength", CommandEvent.class, int.class));

        registry.adapt(context);

        assertTrue(context.isCancelled());
    }

    @Test
    public void adapt_withMoreInputThanParameters_ShouldNotCancel() throws NoSuchMethodException {
        CommandContext context = buildContext(buildCommand("inputLength", CommandEvent.class, int.class), "1", "2");

        registry.adapt(context);

        assertFalse(context.isCancelled());
    }

    @Test
    public void adapt_withOptionalWithDefaultNull_ShouldAddNull() throws NoSuchMethodException {
        CommandContext context = buildContext(buildCommand("optionalNull", CommandEvent.class, int.class));

        registry.adapt(context);

        assertNull(context.getArguments().get(1));
    }

    @Test
    public void adapt_withOptionalWithDefault_ShouldAddDefault() throws NoSuchMethodException {
        CommandContext context = buildContext(buildCommand("optionalDefault", CommandEvent.class, String.class));

        registry.adapt(context);

        assertEquals(TypeAdapterRegistryTestController.OPTIONAL_DEFAULT, context.getArguments().get(1));
    }

    @Test
    public void adapt_withMissingTypeAdapter_ShouldThrowIllegalArgumentException() throws NoSuchMethodException {
        adapter.register(CustomType.class, new CustomTypeAdapter());
        CommandContext context = buildContext(buildCommand("noAdapter", CommandEvent.class, CustomType.class), "string");
        adapter.unregister(CustomType.class);

        assertThrows(IllegalArgumentException.class, () -> registry.adapt(context));
    }

    @Test
    public void adapt_withWrongArgument_ShouldCancel() throws NoSuchMethodException {
        CommandContext context = buildContext(buildCommand("wrongArgument", CommandEvent.class, int.class), "string");

        registry.adapt(context);
        assertTrue(context.isCancelled());
    }

    private CommandDefinition buildCommand(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = controller.getMethod(name, parameterTypes);
        CommandDefinition command = CommandDefinition.build(method, instance, adapter, validator).orElse(null);
        assertNotNull(command);
        return command;
    }

    private CommandContext buildContext(CommandDefinition command, String... input) {
        CommandContext context = new CommandContext(
                new MessageReceivedEventMock(true),
                new JDACommandsMock(),
                new GuildSettings(),
                new ImplementationRegistry(new DependencyInjector(), new FilterRegistry(), new TypeAdapterRegistry(), new ValidatorRegistry()));
        context.setInput(input);
        context.setCommand(command);
        return context;
    }

    private <T> T giveNull() {
        return null;
    }

}
