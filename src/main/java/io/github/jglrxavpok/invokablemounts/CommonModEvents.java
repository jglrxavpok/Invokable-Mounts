package io.github.jglrxavpok.invokablemounts;

import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
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
    }

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof Pig) {
            Entity sourceEntity = event.getSource().getEntity();
            if(sourceEntity instanceof Player player) {
                InteractionHand hand = InteractionHand.MAIN_HAND;
                ItemStack lanternStack = player.getItemInHand(hand);

                // check whether player holds the lantern to capture the soul

                // TODO: tags?
                if(lanternStack.getItem() != InvokableMountsMod.Items.OVERWORLD_LANTERN.get()) {
                    hand = InteractionHand.OFF_HAND;
                    lanternStack = player.getItemInHand(hand);
                    if(lanternStack.getItem() != InvokableMountsMod.Items.OVERWORLD_LANTERN.get()) {
                        return;
                    }
                }

                ItemStack soulItemStack = new ItemStack(InvokableMountsMod.Items.PIG_SOUL.get());
                player.setItemInHand(hand, soulItemStack);
            }
        }
    }
}
