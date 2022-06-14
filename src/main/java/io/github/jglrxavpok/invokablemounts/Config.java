package io.github.jglrxavpok.invokablemounts;

import io.github.jglrxavpok.invokablemounts.entities.*;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {

    public static final Config INSTANCE;
    public static final ForgeConfigSpec FORGE_SPEC;

    static {
        Pair<Config, ForgeConfigSpec> ForgeConfigPair = new ForgeConfigSpec.Builder()
                .configure(Config::new);

        INSTANCE = ForgeConfigPair.getLeft();
        FORGE_SPEC = ForgeConfigPair.getRight();
    }

    public ForgeConfigSpec.ConfigValue<Double> PIG_INVOCATION_RELOAD_TIME;
    public ForgeConfigSpec.ConfigValue<Double> PIG_INVOCATION_DURATION;
    public ForgeConfigSpec.ConfigValue<Double> PIG_INVOCATION_HEALTH;

    public ForgeConfigSpec.ConfigValue<Double> HORSE_INVOCATION_RELOAD_TIME;
    public ForgeConfigSpec.ConfigValue<Double> HORSE_INVOCATION_HEALTH;
    public ForgeConfigSpec.ConfigValue<Double> HORSE_INVOCATION_DURATION;

    public ForgeConfigSpec.ConfigValue<Double> STRIDER_INVOCATION_RELOAD_TIME;
    public ForgeConfigSpec.ConfigValue<Double> STRIDER_INVOCATION_HEALTH;
    public ForgeConfigSpec.ConfigValue<Double> STRIDER_INVOCATION_DURATION;

    public ForgeConfigSpec.ConfigValue<Double> PHANTOM_INVOCATION_RELOAD_TIME;
    public ForgeConfigSpec.ConfigValue<Double> PHANTOM_INVOCATION_HEALTH;
    public ForgeConfigSpec.ConfigValue<Double> PHANTOM_INVOCATION_DURATION;
    public ForgeConfigSpec.ConfigValue<Integer> PHANTOM_INVOCATION_COOLDOWN;


    public ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_INVOCATION_RELOAD_TIME;
    public ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_INVOCATION_HEALTH;
    public ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_INVOCATION_DURATION;

    private Config(ForgeConfigSpec.Builder builder) {
        PIG_INVOCATION_HEALTH = builder
                .comment("Health of the pig invocation ( = hearts *2)")
                .translation("config." + PigMount.ID + ".health")
                .define(List.of(PigMount.ID, "health"), 2.0);

        PIG_INVOCATION_DURATION = builder
                .comment("Average duration of the pig invocation (in seconds)")
                .translation("config." + PigMount.ID + ".duration")
                .define(List.of(PigMount.ID, "duration"), 300.0);

        PIG_INVOCATION_RELOAD_TIME = builder
                .comment("Time taken to fully reload the pig invocation (in seconds)")
                .translation("config." + PigMount.ID + ".reload_time")
                .define(List.of(PigMount.ID, "reload_time"), 20.0);

        // --

        HORSE_INVOCATION_HEALTH = builder
                .comment("Health of the horse invocation ( = hearts *2)")
                .translation("config." + HorseMount.ID + ".health")
                .define(List.of(HorseMount.ID, "health"), 2.0);

        HORSE_INVOCATION_DURATION = builder
                .comment("Average duration of the horse invocation (in seconds)")
                .translation("config." + HorseMount.ID + ".duration")
                .define(List.of(HorseMount.ID, "duration"), 120.0);

        HORSE_INVOCATION_RELOAD_TIME = builder
                .comment("Time taken to fully reload the horse invocation (in seconds)")
                .translation("config." + HorseMount.ID + ".reload_time")
                .define(List.of(HorseMount.ID, "reload_time"), 5.0);

        // --

        STRIDER_INVOCATION_HEALTH = builder
                .comment("Health of the strider invocation ( = hearts *2)")
                .translation("config." + StriderMount.ID + ".health")
                .define(List.of(StriderMount.ID, "health"), 2.0);

        STRIDER_INVOCATION_DURATION = builder
                .comment("Average duration of the strider invocation (in seconds)")
                .translation("config." + StriderMount.ID + ".duration")
                .define(List.of(StriderMount.ID, "duration"), 60.0);

        STRIDER_INVOCATION_RELOAD_TIME = builder
                .comment("Time taken to fully reload the strider invocation (in seconds)")
                .translation("config." + StriderMount.ID + ".reload_time")
                .define(List.of(StriderMount.ID, "reload_time"), 10.0);

        // --

        PHANTOM_INVOCATION_HEALTH = builder
                .comment("Health of the phantom invocation ( = hearts *2)")
                .translation("config." + PhantomMount.ID + ".health")
                .define(List.of(PhantomMount.ID, "health"), 2.0);

        PHANTOM_INVOCATION_DURATION = builder
                .comment("Average duration of the phantom invocation (in seconds)")
                .translation("config." + PhantomMount.ID + ".duration")
                .define(List.of(PhantomMount.ID, "duration"), 30.0);

        PHANTOM_INVOCATION_RELOAD_TIME = builder
                .comment("Time taken to fully reload the phantom invocation (in seconds)")
                .translation("config." + PhantomMount.ID + ".reload_time")
                .define(List.of(PhantomMount.ID, "reload_time"), 10.0);

        PHANTOM_INVOCATION_COOLDOWN = builder
                .comment("How many ticks of cooldown after using the phantom invoker item?")
                .translation("config." + PhantomMount.ID + ".cooldown")
                .define(List.of(PhantomMount.ID, "cooldown"), 50);

        // --

        ENDER_DRAGON_INVOCATION_HEALTH = builder
                .comment("Health of the dragon invocation ( = hearts *2)")
                .translation("config." + EnderDragonMount.ID + ".health")
                .define(List.of(EnderDragonMount.ID, "health"), 2.0);

        ENDER_DRAGON_INVOCATION_DURATION = builder
                .comment("Average duration of the dragon invocation (in seconds)")
                .translation("config." + EnderDragonMount.ID + ".duration")
                .define(List.of(EnderDragonMount.ID, "duration"), 180.0);

        ENDER_DRAGON_INVOCATION_RELOAD_TIME = builder
                .comment("Time taken to fully reload the dragon invocation (in seconds)")
                .translation("config." + EnderDragonMount.ID + ".reload_time")
                .define(List.of(EnderDragonMount.ID, "reload_time"), 60.0);
    }
}
