package io.github.jglrxavpok.invokablemounts.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PhantomMount extends Phantom implements MountEntity {

    private static final EntityDataAccessor<Integer> REMAINING_TICKS = SynchedEntityData.defineId(PhantomMount.class, EntityDataSerializers.INT);

    public PhantomMount(EntityType<? extends Phantom> entityType, Level level) {
        super(entityType, level);
        noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.removeAllGoals();
    }

    @Override
    public void tick() {
        super.tick();

        tick_Mount();

        if(getVehicle() instanceof Player player) {
            setYRot(player.getYRot());
            setPos(player.getPosition(1.0f));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        defineSynchedData_Mount();
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        return false;
    }

    @Override
    public boolean keepMountAlive() {
        return getVehicle() instanceof Player;
    }

    @Override
    public EntityDataAccessor<Integer> getRemainingTicksAccessor() {
        return REMAINING_TICKS;
    }
}
