package io.github.jglrxavpok.invokablemounts;

import com.mojang.logging.LogUtils;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
import io.github.jglrxavpok.invokablemounts.items.HorseInvocationItem;
import io.github.jglrxavpok.invokablemounts.items.InvocationItem;
import io.github.jglrxavpok.invokablemounts.items.PigInvocationItem;
import io.github.jglrxavpok.invokablemounts.items.SoulStealingLanternItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(InvokableMountsMod.MODID)
public class InvokableMountsMod
{
    public static class Entities
    {
        public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES,
                InvokableMountsMod.MODID);

        public static final RegistryObject<EntityType<PigMount>> PIG_MOUNT = REGISTRY.register("pig_mount", () -> {
            EntityType.EntityFactory<PigMount> factory = PigMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(0.9F, 0.9F)
                    .clientTrackingRange(10)
                    .build("pig_mount");
        });

        public static final RegistryObject<EntityType<HorseMount>> HORSE_MOUNT = REGISTRY.register("horse_mount", () -> {
            EntityType.EntityFactory<HorseMount> factory = HorseMount::new;
            return EntityType.Builder.of(factory, MobCategory.CREATURE)
                    .fireImmune()
                    .noSummon()
                    .sized(1.3964844F, 1.6F)
                    .clientTrackingRange(10)
                    .build("horse_mount");
        });
    }

    public static class Blocks
    {
        public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS,
                InvokableMountsMod.MODID);
    }

    public static class Items
    {
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

        public static final RegistryObject<Item> OVERWORLD_LANTERN = REGISTRY.register("overworld_lantern",
                () -> new SoulStealingLanternItem());

        public static final RegistryObject<Item> PIG_SOUL = REGISTRY.register("pig_soul",
                () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1)));
    }

    public static final String MODID = "invokablemounts";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public InvokableMountsMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

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
