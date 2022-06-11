package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
import io.github.jglrxavpok.invokablemounts.entities.StriderMount;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StriderInvocationItem extends InvocationItem<StriderMount> {
    public StriderInvocationItem(boolean active, String baseID) {
        super(active, baseID);
    }

    @Override
    public boolean isRidingCorrespondingEntity(Player player) {
        return player.getVehicle() instanceof StriderMount;
    }

    @Override
    public InteractionResultHolder<ItemStack> handleUseWhileRiding(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(player.getVehicle() instanceof StriderMount mount) {
            mount.boost();
            itemstack.setDamageValue(itemstack.getDamageValue() + 10);

            return InteractionResultHolder.success(itemstack);
        }

        return super.handleUseWhileRiding(level, player, hand);
    }
    @Override
    public StriderMount generateEntity(Level level) {
        return new StriderMount(InvokableMountsMod.Entities.STRIDER_MOUNT.get(), level);
    }
}
