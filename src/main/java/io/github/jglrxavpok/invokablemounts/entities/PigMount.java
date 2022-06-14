package io.github.jglrxavpok.invokablemounts.entities;

import io.github.jglrxavpok.invokablemounts.Config;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PigMount extends Pig implements MountEntity {

    public static final String ID = "pig_mount";
    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(PigMount.class, EntityDataSerializers.INT);

    public PigMount(EntityType<? extends PigMount> entityType, Level level) {
        super(entityType, level);
        this.steering.setSaddle(true);
    }

    @Override
    public boolean isBaby() {
        return false;
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

    public static AttributeSupplier.Builder createAttributes() {
        return Pig.createMobAttributes().add(Attributes.MAX_HEALTH, Config.INSTANCE.PIG_INVOCATION_HEALTH.get());
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
    public void tick() {
        super.tick();

        tick_Mount();
    }

    @Override
    public boolean isFood(ItemStack p_29508_) {
        return false;
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public EntityDataAccessor<Integer> getRemainingTicksAccessor() {
        return REMAINING_TICKS;
    }
}
