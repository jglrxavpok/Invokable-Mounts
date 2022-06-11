package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.EnderDragonMount;
import io.github.jglrxavpok.invokablemounts.entities.StriderMount;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EnderDragonInvocationItem extends InvocationItem<EnderDragonMount> {
    public EnderDragonInvocationItem(boolean active, String baseID) {
        super(active, baseID);
    }

    @Override
    public boolean isRidingCorrespondingEntity(Player player) {
        return player.getVehicle() instanceof EnderDragonMount;
    }

    @Override
    public InteractionResultHolder<ItemStack> handleUseWhileRiding(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(player.getVehicle() instanceof EnderDragonMount mount) {
            // TODO: attack, breath ?

            return InteractionResultHolder.success(itemstack);
        }

        return super.handleUseWhileRiding(level, player, hand);
    }
    @Override
    public EnderDragonMount generateEntity(Level level) {
        return new EnderDragonMount(InvokableMountsMod.Entities.ENDER_DRAGON_MOUNT.get(), level);
    }
}
