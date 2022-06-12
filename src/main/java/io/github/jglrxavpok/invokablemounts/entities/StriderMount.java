package io.github.jglrxavpok.invokablemounts.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class StriderMount extends Strider implements MountEntity {

    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(StriderMount.class, EntityDataSerializers.INT);

    public StriderMount(EntityType<? extends StriderMount> entityType, Level level) {
        super(entityType, level);

        this.steering.setSaddle(true);

        // TODO: config for health on spawn
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        if(isVehicle() && getFirstPassenger() instanceof Player player) {
            return player;
        }
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        defineSynchedData_Mount();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.removeAllGoals();
    }

    @Override
    public void tick() {
        super.tick();

        tick_Mount();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Strider.createAttributes();
    }

    @Override
    public EntityDataAccessor<Integer> getRemainingTicksAccessor() {
        return REMAINING_TICKS;
    }
}
