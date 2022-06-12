package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.PhantomMount;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PhantomInvocationItem extends InvocationItem<PhantomMount> {

    public PhantomInvocationItem(boolean active, String baseID) {
        super(active, baseID);
    }

    @Override
    public void startActiveState(Level level, ItemStack stack, Player player) {
        // spawn phantom ghost entity
        PhantomMount mount = generateEntity(level);
        if(stack.hasCustomHoverName()) {
            mount.setCustomName(stack.getHoverName());
            mount.setCustomNameVisible(true);
        }
        mount.setPos(player.getPosition(1.0f));

        mount.startRiding(player, true); // reversal compared to other mounts

        // TODO: mount.playSound();
        level.addFreshEntity(mount);

        // make player fly
        player.startFallFlying();
        if(player instanceof LocalPlayer p) {
            p.connection.send(new ServerboundPlayerCommandPacket(p, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        }
    }

    @Override
    public PhantomMount generateEntity(Level level) {
        return new PhantomMount(InvokableMountsMod.Entities.PHANTOM_MOUNT.get(), level);
    }

    @Override
    public boolean shouldStayActive(Level level, ItemStack stack, Player player) {
        return !player.isOnGround() && player.isFallFlying();
    }

    @Override
    public void finishActiveState(Level level, ItemStack stack, Player player) {
        super.finishActiveState(level, stack, player);
        if(player.getFirstPassenger() instanceof PhantomMount m) {
            m.unRide();
        }
    }

    @Override
    public boolean isRidingCorrespondingEntity(Player player) {
        return true;
    }
}
