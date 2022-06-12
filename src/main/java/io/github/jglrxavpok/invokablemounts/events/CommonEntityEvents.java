package io.github.jglrxavpok.invokablemounts.events;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.EnderDragonMount;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InvokableMountsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEntityEvents {

    public static void attemptToCaptureSoul(LivingEntity killed, Player player, Item requiredItem, Item soulItem) {
        InteractionHand hand = InteractionHand.MAIN_HAND;
        ItemStack lanternStack = player.getItemInHand(hand);

        // check whether player holds the lantern to capture the soul

        // TODO: tags?
        if(lanternStack.getItem() != requiredItem) {
            hand = InteractionHand.OFF_HAND;
            lanternStack = player.getItemInHand(hand);
            if(lanternStack.getItem() != requiredItem) {
                return;
            }
        }

        killed.skipDropExperience(); // we don't want sculk to steal the soul, that's our job.

        ItemStack soulItemStack = new ItemStack(soulItem);
        player.setItemInHand(hand, soulItemStack);
    }

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event) {
        Entity sourceEntity = event.getSource().getEntity();
        if(sourceEntity instanceof Player player) {
            if (event.getEntity() instanceof Pig) {
                attemptToCaptureSoul(event.getEntityLiving(), player, InvokableMountsMod.Items.SOUL_STEALING_CANDLE.get(), InvokableMountsMod.Items.PIG_SOUL.get());
            }
            if (event.getEntity() instanceof Horse) {
                attemptToCaptureSoul(event.getEntityLiving(), player, InvokableMountsMod.Items.SOUL_STEALING_CANDLE.get(), InvokableMountsMod.Items.HORSE_SOUL.get());
            }
            if (event.getEntity() instanceof Strider) {
                attemptToCaptureSoul(event.getEntityLiving(), player, InvokableMountsMod.Items.OTHERWORLDLY_SOUL_STEALING_CANDLE.get(), InvokableMountsMod.Items.STRIDER_SOUL.get());
            }
            if (event.getEntity() instanceof Phantom) {
                attemptToCaptureSoul(event.getEntityLiving(), player, InvokableMountsMod.Items.OTHERWORLDLY_SOUL_STEALING_CANDLE.get(), InvokableMountsMod.Items.PHANTOM_SOUL.get());
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDestroyingBlock(LivingDestroyBlockEvent event) {
        if(event.getEntity() instanceof EnderDragonMount) {
            // just don't break blocks, thaaanks
            event.setCanceled(true);
        }
    }
}
