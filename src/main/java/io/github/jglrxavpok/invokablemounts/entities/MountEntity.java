package io.github.jglrxavpok.invokablemounts.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/**
 * Common interface for mounts of this mod. Allows code reuse between different entities
 * (because we cannot do multiple inheritance)
 */
public interface MountEntity {

    /**
     * Mounts despawn after some time once unmounted. How long before it actually disappears?
     */
    int TICK_COUNT_PER_DESPAWN = 20;

    /**
     * For how many ticks should we spawn soul particles when the entity dies?
     * (When the entity is dying, it will spawn starting at TICK_COUNT_PER_DESPAWN-TICKS_WITH_PARTICLES until TICK_COUNT_PER_DESPAWN)
     */
    int TICKS_WITH_PARTICLES = 5;

    /**
     * When to play the death sound?
     */
    int TICK_DEATH_SOUND = 5;

    /**
     * Convenience method
     */
    default LivingEntity asEntity() {
        return (LivingEntity)this;
    }

    /**
     * DataAccessor used to sync remaining ticks before disappearing of this entity
     */
    EntityDataAccessor<Integer> getRemainingTicksAccessor();

    default int getRemainingTicksBeforeDisappearing() {
        return asEntity().getEntityData().get(getRemainingTicksAccessor());
    }

    /**
     * Called when the mount should spawn particle for its death
     */
    default void onDespawnParticleSpawn() {
        asEntity().spawnSoulSpeedParticle();
    }

    /**
     * Called when the mount despawns
     */
    default void onMountDespawn() {}

    default void setRemainingTicksBeforeDisappearing(int ticks) {
        asEntity().getEntityData().set(getRemainingTicksAccessor(), ticks);
    }

    // Behavior code, have to be called from entity code

    default void defineSynchedData_Mount() {
        asEntity().getEntityData().define(getRemainingTicksAccessor(), TICK_COUNT_PER_DESPAWN);
    }

    default boolean keepMountAlive() {
        return asEntity().isVehicle();
    }

    default void tick_Mount() {
        int remainingTicks = getRemainingTicksBeforeDisappearing();
        if(asEntity().level.isClientSide) {
            if(remainingTicks < TICKS_WITH_PARTICLES) {
                onDespawnParticleSpawn();
            }
        } else {
            if(!keepMountAlive()) {
                if(remainingTicks-- <= 0) {
                    asEntity().remove(Entity.RemovalReason.DISCARDED);
                } else if(remainingTicks == TICK_DEATH_SOUND) {
                    asEntity().playSound(SoundEvents.CANDLE_EXTINGUISH, 1.0f, 0.8f);
                }

                setRemainingTicksBeforeDisappearing(remainingTicks);
            } else {
                setRemainingTicksBeforeDisappearing(TICK_COUNT_PER_DESPAWN);
            }
        }
    }

}
