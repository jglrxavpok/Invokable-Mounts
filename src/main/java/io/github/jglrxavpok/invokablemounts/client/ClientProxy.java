package io.github.jglrxavpok.invokablemounts.client;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.client.renderers.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = InvokableMountsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(InvokableMountsMod.Entities.PIG_MOUNT.get(), PigMountRenderer::new);
        event.registerEntityRenderer(InvokableMountsMod.Entities.HORSE_MOUNT.get(), HorseMountRenderer::new);
        event.registerEntityRenderer(InvokableMountsMod.Entities.STRIDER_MOUNT.get(), StriderMountRenderer::new);
        event.registerEntityRenderer(InvokableMountsMod.Entities.PHANTOM_MOUNT.get(), PhantomMountRenderer::new);
        event.registerEntityRenderer(InvokableMountsMod.Entities.ENDER_DRAGON_MOUNT.get(), EnderDragonMountRenderer::new);
    }
}
