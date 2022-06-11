package io.github.jglrxavpok.invokablemounts.events;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.EnderDragonMount;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
import io.github.jglrxavpok.invokablemounts.entities.StriderMount;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InvokableMountsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(InvokableMountsMod.Entities.PIG_MOUNT.get(), PigMount.createAttributes().build());
        event.put(InvokableMountsMod.Entities.HORSE_MOUNT.get(), HorseMount.createAttributes().build());
        event.put(InvokableMountsMod.Entities.STRIDER_MOUNT.get(), StriderMount.createAttributes().build());
        event.put(InvokableMountsMod.Entities.ENDER_DRAGON_MOUNT.get(), EnderDragonMount.createAttributes().build());
    }
}
