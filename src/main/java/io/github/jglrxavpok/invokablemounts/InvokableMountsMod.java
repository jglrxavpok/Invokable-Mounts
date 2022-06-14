package io.github.jglrxavpok.invokablemounts;

import com.mojang.logging.LogUtils;
import io.github.jglrxavpok.invokablemounts.entities.*;
import io.github.jglrxavpok.invokablemounts.items.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod(InvokableMountsMod.MODID)
public class InvokableMountsMod {
    public static class Entities {
        public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES,
                InvokableMountsMod.MODID);

        public static final RegistryObject<EntityType<PigMount>> PIG_MOUNT = REGISTRY.register(PigMount.ID, () -> {
            EntityType.EntityFactory<PigMount> factory = PigMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(0.9F, 0.9F)
                    .clientTrackingRange(10)
                    .build(PigMount.ID);
        });

        public static final RegistryObject<EntityType<HorseMount>> HORSE_MOUNT = REGISTRY.register(HorseMount.ID, () -> {
            EntityType.EntityFactory<HorseMount> factory = HorseMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(1.3964844F, 1.6F)
                    .clientTrackingRange(10)
                    .build(HorseMount.ID);
        });

        public static final RegistryObject<EntityType<StriderMount>> STRIDER_MOUNT = REGISTRY.register(StriderMount.ID, () -> {
            EntityType.EntityFactory<StriderMount> factory = StriderMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(0.9F, 1.7F)
                    .clientTrackingRange(10)
                    .build(StriderMount.ID);
        });

        public static final RegistryObject<EntityType<PhantomMount>> PHANTOM_MOUNT = REGISTRY.register(PhantomMount.ID, () -> {
            EntityType.EntityFactory<PhantomMount> factory = PhantomMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(0.9F, 0.5F)
                    .clientTrackingRange(10)
                    .build(PhantomMount.ID);
        });

        public static final RegistryObject<EntityType<EnderDragonMount>> ENDER_DRAGON_MOUNT = REGISTRY.register(EnderDragonMount.ID, () -> {
            EntityType.EntityFactory<EnderDragonMount> factory = EnderDragonMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(16.0F, 8.0F)
                    .clientTrackingRange(10)
                    .build(EnderDragonMount.ID);
        });
    }

    public static class Blocks {
        public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS,
                InvokableMountsMod.MODID);
    }

    public static class Items {
        public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS,
                InvokableMountsMod.MODID);

        public static final RegistryObject<Item> PIG_INVOKER = REGISTRY.register("inactive_pig_invoker",
                () -> new PigInvocationItem(false, "pig_invoker"));

        public static final RegistryObject<Item> ACTIVE_PIG_INVOKER = REGISTRY.register("active_pig_invoker",
                () -> new PigInvocationItem(true, "pig_invoker"));

        public static final RegistryObject<Item> HORSE_INVOKER = REGISTRY.register("inactive_horse_invoker",
                () -> new HorseInvocationItem(false, "horse_invoker"));

        public static final RegistryObject<Item> ACTIVE_HORSE_INVOKER = REGISTRY.register("active_horse_invoker",
                () -> new HorseInvocationItem(true, "horse_invoker"));

        public static final RegistryObject<Item> STRIDER_INVOKER = REGISTRY.register("inactive_strider_invoker",
                () -> new StriderInvocationItem(false, "strider_invoker"));

        public static final RegistryObject<Item> ACTIVE_STRIDER_INVOKER = REGISTRY.register("active_strider_invoker",
                () -> new StriderInvocationItem(true, "strider_invoker"));

        public static final RegistryObject<Item> PHANTOM_INVOKER = REGISTRY.register("inactive_phantom_invoker",
                () -> new PhantomInvocationItem(false, "phantom_invoker"));

        public static final RegistryObject<Item> ACTIVE_PHANTOM_INVOKER = REGISTRY.register("active_phantom_invoker",
                () -> new PhantomInvocationItem(true, "phantom_invoker"));

        public static final RegistryObject<Item> ENDER_DRAGON_INVOKER = REGISTRY.register("inactive_ender_dragon_invoker",
                () -> new EnderDragonInvocationItem(false, "ender_dragon_invoker"));

        public static final RegistryObject<Item> ACTIVE_ENDER_DRAGON_INVOKER = REGISTRY.register("active_ender_dragon_invoker",
                () -> new EnderDragonInvocationItem(true, "ender_dragon_invoker"));

        public static final RegistryObject<Item> SOUL_STEALING_CANDLE = REGISTRY.register("soul_stealing_candle",
                () -> new SoulStealingCandleItem());

        public static final RegistryObject<Item> OTHERWORLDLY_SOUL_STEALING_CANDLE = REGISTRY.register("otherworldly_soul_stealing_candle",
                () -> new SoulStealingCandleItem());

        public static final RegistryObject<Item> PIG_SOUL = REGISTRY.register("pig_soul",
                () -> new SoulItem(false));

        public static final RegistryObject<Item> HORSE_SOUL = REGISTRY.register("horse_soul",
                () -> new SoulItem(false));

        public static final RegistryObject<Item> STRIDER_SOUL = REGISTRY.register("strider_soul",
                () -> new SoulItem(true));

        public static final RegistryObject<Item> PHANTOM_SOUL = REGISTRY.register("phantom_soul",
                () -> new SoulItem(true));
    }

    public static final String MODID = "invokablemounts";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public InvokableMountsMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.FORGE_SPEC);
        final var bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        bus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        bus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        bus.addListener(this::processIMC);

        Blocks.REGISTRY.register(bus);
        Items.REGISTRY.register(bus);
        Entities.REGISTRY.register(bus);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
