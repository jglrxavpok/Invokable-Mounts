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
        final int cooldown = 50; // TODO: make configurable
        player.getCooldowns().addCooldown(InvokableMountsMod.Items.PHANTOM_INVOKER.get(), cooldown);
        player.getCooldowns().addCooldown(InvokableMountsMod.Items.ACTIVE_PHANTOM_INVOKER.get(), cooldown);
    }

    @Override
    public boolean isRidingCorrespondingEntity(Player player) {
        return true;
    }

    // --------------------

    public enum FlyBehaviourOverride {
        /**
         * Default behaviour, no override
         */
        DEFAULT,

        /**
         * Allow flight, even if vanilla does not authorize it
         */
        ALLOW_FLIGHT,

        /**
         * Disallow flight, even if vanilla does not authorize it
         */
        DISALLOW_FLIGHT,
    }

    public static boolean isCurrentlyFlyingWithMount(Player player) {
        return player.getInventory().countItem(InvokableMountsMod.Items.ACTIVE_PHANTOM_INVOKER.get()) > 0;
    }

    public static FlyBehaviourOverride overrideFlyBehaviour(Player player) {
        boolean hasProperMount = isCurrentlyFlyingWithMount(player);
        if(!hasProperMount) {
            return FlyBehaviourOverride.DEFAULT;
        }

        // from here we assume the player has the phantom mount, so we are in full control: we don't have to care if elytras are present

        boolean onGround = player.isOnGround();
        if(onGround) {
            return FlyBehaviourOverride.DISALLOW_FLIGHT;
        }

        boolean inWater = player.isInWater();
        if(inWater) {
            return FlyBehaviourOverride.DISALLOW_FLIGHT;
        }

        return FlyBehaviourOverride.ALLOW_FLIGHT;
    }

    /**
     * Force deactivate all active phantom invokers of a given player
     * @param player
     */
    public static void stopFlyingWithMount(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if(stack.is(InvokableMountsMod.Items.ACTIVE_PHANTOM_INVOKER.get())) {
                ItemStack copy = stack.copy();
                copy.setDamageValue(copy.getMaxDamage());
                player.getInventory().setItem(i, copy);
            }
        }
    }
}
