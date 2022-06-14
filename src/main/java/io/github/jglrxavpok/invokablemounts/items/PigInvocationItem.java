package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.Config;
import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.PigMount;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PigInvocationItem extends InvocationItem<PigMount> {
    public PigInvocationItem(boolean active, String baseID) {
        super(active, baseID);
    }

    @Override
    public boolean isRidingCorrespondingEntity(Player player) {
        return player.getVehicle() instanceof PigMount;
    }

    @Override
    public InteractionResultHolder<ItemStack> handleUseWhileRiding(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(player.getVehicle() instanceof PigMount mount) {
            mount.boost();
            itemstack.setDamageValue(itemstack.getDamageValue() + 10);

            return InteractionResultHolder.success(itemstack);
        }

        return super.handleUseWhileRiding(level, player, hand);
    }

    @Override
    public PigMount generateEntity(Level level) {
        return new PigMount(InvokableMountsMod.Entities.PIG_MOUNT.get(), level);
    }

    @Override
    public double getAverageDuration() {
        return Config.INSTANCE.PIG_INVOCATION_DURATION.get();
    }

    @Override
    public double getReloadTime() {
        return Config.INSTANCE.PIG_INVOCATION_RELOAD_TIME.get();
    }
}
