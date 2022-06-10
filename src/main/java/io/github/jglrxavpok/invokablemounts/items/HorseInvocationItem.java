package io.github.jglrxavpok.invokablemounts.items;

import io.github.jglrxavpok.invokablemounts.InvokableMountsMod;
import io.github.jglrxavpok.invokablemounts.entities.HorseMount;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HorseInvocationItem extends InvocationItem<HorseMount> {
    public HorseInvocationItem(boolean active, String baseID) {
        super(active, baseID);
    }

    @Override
    public boolean isRidingCorrespondingEntity(Player player) {
        return player.getVehicle() instanceof HorseMount;
    }

    @Override
    public HorseMount generateEntity(Level level) {
        return new HorseMount(InvokableMountsMod.Entities.HORSE_MOUNT.get(), level);
    }
}
